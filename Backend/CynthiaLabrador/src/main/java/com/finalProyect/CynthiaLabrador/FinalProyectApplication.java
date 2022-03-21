package com.finalProyect.CynthiaLabrador;

import com.finalProyect.CynthiaLabrador.config.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(StorageProperties.class)
@SpringBootApplication
public class FinalProyectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinalProyectApplication.class, args);
	}

}
