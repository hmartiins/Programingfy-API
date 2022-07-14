package com.example.logusletterapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogusLetterApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogusLetterApiApplication.class, args);
	}

}
