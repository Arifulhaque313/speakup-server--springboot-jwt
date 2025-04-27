package com.practice.speakup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@SpringBootApplication
@EnableMethodSecurity
public class SpeakupApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpeakupApplication.class, args);
	}

}
