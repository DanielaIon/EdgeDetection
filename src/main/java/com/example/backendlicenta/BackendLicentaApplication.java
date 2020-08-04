package com.example.backendlicenta;

import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendLicentaApplication {

	public static void main(String[] args) {

		OpenCV.loadShared();
		SpringApplication.run(BackendLicentaApplication.class, args);
	}

}
