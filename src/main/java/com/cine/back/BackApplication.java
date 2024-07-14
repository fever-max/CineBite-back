package com.cine.back;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class BackApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);

		log.info("Info level log message / 정보 메세지 예시");
		log.error("Error level log message / 오류 메세지 예시");
	}

}