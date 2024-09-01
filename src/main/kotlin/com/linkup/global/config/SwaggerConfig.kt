package com.kakaotalk.global.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpHeaders

@Configuration
class SwaggerConfig {
    @Bean
    fun api(): OpenAPI = OpenAPI().info(Info().title("KakaoTalk").description("KakaoTalk API Documentation").version("v1.0"))
        .servers(listOf(
            Server().apply { url = "https://119b-175-202-245-36.ngrok-free.app" },
            Server().apply { url = "http://localhost:8080" },
        ))
        .addSecurityItem(SecurityRequirement().addList("Authorization"))
        .components(
            Components()
                .addSecuritySchemes(
                    "Authorization", SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("Bearer")
                        .bearerFormat("Authorization")
                        .`in`(SecurityScheme.In.HEADER)
                        .name(HttpHeaders.AUTHORIZATION)
                )
        )
}