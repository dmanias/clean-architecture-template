package com.cleanarchitecture;

import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Clean Architecture API",
				version = "1.0",
				description = "API Documentation for Clean Architecture Template",
				contact = @Contact(
						name = "Your Name",
						email = "your.email@example.com"
				),
				license = @License(
						name = "MIT License",
						url = "https://opensource.org/licenses/MIT"
				)
		)
)
public class CleanArchitectureTemplateApplication {
	public static void main(String[] args) {
		SpringApplication.run(CleanArchitectureTemplateApplication.class, args);
	}
}
