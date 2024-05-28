package com.cine.back;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackApplication {

	private static final Logger LOGGER = LogManager.getLogger(BackApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);

		LOGGER.info("Info level log message / 정보 메세지 예시");
		LOGGER.error("Error level log message / 오류 메세지 예시");
	}

}