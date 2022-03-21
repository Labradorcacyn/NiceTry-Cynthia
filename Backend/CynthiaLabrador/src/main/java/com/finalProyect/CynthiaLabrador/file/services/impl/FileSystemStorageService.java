package com.finalProyect.CynthiaLabrador.file.services.impl;

import com.finalProyect.CynthiaLabrador.config.StorageProperties;
import com.finalProyect.CynthiaLabrador.errors.excepciones.StorageException;
import com.finalProyect.CynthiaLabrador.file.services.StorageService;
import com.finalProyect.CynthiaLabrador.utils.MediaTypeUrlResource;
import com.finalProyect.CynthiaLabrador.errors.excepciones.FileNotFoundException;
import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.IVAudioAttributes;
import io.github.techgnious.dto.IVSize;
import io.github.techgnious.dto.IVVideoAttributes;
import io.github.techgnious.dto.VideoFormats;
import io.github.techgnious.exception.VideoException;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;

    public static BufferedImage simpleImageResizer(BufferedImage bufferedImage, int targetWitdth){
        return Scalr.resize(bufferedImage, targetWitdth);
    }

    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @PostConstruct
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage location", e);
        }
    }

    @Override
    public String escalar(MultipartFile file, int size) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        String extension = StringUtils.getFilenameExtension(filename);
        String name = filename.replace("."+ extension, "");

        BufferedImage img = ImageIO.read(file.getInputStream());

        BufferedImage escaleImg = simpleImageResizer(img, size);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        ImageIO.write(escaleImg, extension, baos);

        InputStream inputStream2 = new ByteArrayInputStream(baos.toByteArray());

        try {
            if (file.isEmpty())
                throw new StorageException("El fichero subido está vacío");


            while(Files.exists(rootLocation.resolve(filename))) {
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);

                filename = name + "_" + suffix + "." + extension;
            }
            try (InputStream inputStream = inputStream2) {
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + filename, ex);
        }
        return filename;
    }

    @Override
    public String escaleVideo(MultipartFile file) throws IOException, VideoException {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String extension = StringUtils.getFilenameExtension(filename);
        String name = filename.replace("."+ extension, "");

        IVCompressor compressor = new IVCompressor();
        IVSize customRes = new IVSize();
        customRes.setWidth(400);
        customRes.setHeight(300);
        IVAudioAttributes audioAttribute = new IVAudioAttributes();
        audioAttribute.setBitRate(64000);
        audioAttribute.setChannels(2);
        audioAttribute.setSamplingRate(44100);
        IVVideoAttributes videoAttribute = new IVVideoAttributes();

        videoAttribute.setBitRate(160000);

        videoAttribute.setFrameRate(15);
        videoAttribute.setSize(customRes);
        byte[] video = compressor.encodeVideoWithAttributes(file.getBytes(), VideoFormats.MP4,audioAttribute, videoAttribute);

        InputStream inputStream1 = new ByteArrayInputStream(video);
        try {
            if (file.isEmpty())
                throw new StorageException("El fichero subido está vacío");

            while(Files.exists(rootLocation.resolve(filename))) {
                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);

                filename = name + "_" + suffix + "." + extension;
            }
            try (InputStream inputStream = inputStream1) {
                Files.copy(inputStream, rootLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + filename, ex);
        }
        return filename;
    }

    @Override
    public String storeOriginal(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        String newFilename = "";
        try {
            if (file.isEmpty())
                throw new StorageException("El fichero subido está vacío");

            newFilename = filename;
            while(Files.exists(rootLocation.resolve(newFilename))) {
                String extension = StringUtils.getFilenameExtension(newFilename);
                String name = newFilename.replace("."+extension,"");

                String suffix = Long.toString(System.currentTimeMillis());
                suffix = suffix.substring(suffix.length()-6);

                newFilename = name + "_" + suffix + "." + extension;
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, rootLocation.resolve(newFilename),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException ex) {
            throw new StorageException("Error en el almacenamiento del fichero: " + newFilename, ex);
        }
        return newFilename;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Error al leer los ficheros almacenados", e);
        }
    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {

        try {
            Path file = load(filename);
            MediaTypeUrlResource resource = new MediaTypeUrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteFile(Path filename) throws IOException {

        try {
            MediaTypeUrlResource resource = new MediaTypeUrlResource(filename.toUri());
            if (resource.exists() || resource.isReadable()) {
                Files.delete(filename);
            }
            else {
                throw new FileNotFoundException(
                        "Could not read file: " + filename);
            }
        }
        catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
}
