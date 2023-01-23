package com.binar.kelompokd;

import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@OpenAPIDefinition(
		info = @Info(title = "Backend Naqos API", version = "1.0.0", description = "Backend Project for Final Project Binar Synrgy Batch 5 Kelompok D"),
		servers = {@Server(url = "https://be-naqos.up.railway.app/api"), @Server(url = "http://localhost:8080/api")})
@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}

