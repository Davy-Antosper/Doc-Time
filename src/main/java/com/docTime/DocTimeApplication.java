package com.docTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@SpringBootApplication
public class DocTimeApplication {

	public static void main(String[] args) {
		var context = SpringApplication.run(DocTimeApplication.class, args);

		Environment env = context.getEnvironment();

		System.out.println("\n========================================");
		System.out.println(" DocTime est vivant !");
		System.out.println(" Port    : " + env.getProperty("server.port"));
		System.out.println(" Profils : " + Arrays.toString(env.getActiveProfiles()));
		System.out.println(" BDD     : " + env.getProperty("spring.datasource.url"));
		System.out.println("========================================\n");
	}

}
