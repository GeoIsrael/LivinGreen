package com.livingreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class LivingreenApplication {

	public static void main(String[] args) {

		ApiContextInitializer.init();

		SpringApplication.run(LivingreenApplication.class, args);
	}

}
