package com.cine.back.config;

import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;

@OpenAPIDefinition(info = @Info(title = "cineBite API 명세서", description = "모의 프로젝트", version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

}
