package com.binar.kelompokd;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@OpenAPIDefinition
@SpringBootApplication
public class BackendApplication {
	@Value("${appsname}")
	static String APLIKASI_SAYA;

	@Value("${BASEURL}")
	@Autowired
	String URL_STATIC;
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
