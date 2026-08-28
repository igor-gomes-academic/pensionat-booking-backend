package com.pensionat.config;

import jakarta.validation.constraints.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    //Frontend kör port (localhost:3000), backend på en annan (localhost:8080). CorsConfig bekräftar att det är ok
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NotNull CorsRegistry registry) {
                registry.addMapping("/api/**") // regler för URL:er som börjar med /api/. våra endpoints (/api/rooms, /api/bookings)
                        .allowedOrigins("http://localhost:3000") // bara frontend på denna exakta adress får anropa. Allt annat blockeras.
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // vilka HTTP-metoder som tillåt
                        .allowCredentials(true); // tillåt att cookies och auth-headers skickas med. Behövs senare för inloggning.
            }

        };
    }
}