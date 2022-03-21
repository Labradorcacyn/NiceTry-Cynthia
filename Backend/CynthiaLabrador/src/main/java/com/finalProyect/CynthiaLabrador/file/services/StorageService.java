package com.finalProyect.CynthiaLabrador.file.services;

import io.github.techgnious.exception.VideoException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    String storeOriginal(MultipartFile file);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteFile(Path filename) throws IOException;

    String escalar(MultipartFile file, int size) throws IOException;

    String escaleVideo(MultipartFile file) throws IOException, VideoException;

    void deleteAll();

}

