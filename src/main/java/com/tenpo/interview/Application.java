package com.tenpo.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Spring boot implementation class - Defines Spring Boot configuration.
 *
 * @author Agustin-Varela
 */
@SpringBootApplication(scanBasePackages = "com.tenpo")
public class Application {

	/**
	 * Main method to initialize the Spring application.
	 *
	 * @param args Arguments passed by.
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
