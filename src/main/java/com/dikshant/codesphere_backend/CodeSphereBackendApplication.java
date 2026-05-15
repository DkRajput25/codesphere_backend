package com.dikshant.codesphere_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CodeSphereBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeSphereBackendApplication.class, args);
		System.out.println("🚀 CodeSphere backend running on http://localhost:8080");
	}

}
