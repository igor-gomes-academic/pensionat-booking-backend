package com.pensionat.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


//Styr Spring Security. - Configuration ska läsa vid start och plocka upp @Bean-metoderna
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean //reglerna som varje HTTP-request passerar genom
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //Stänger av CSRF-skyddet - REST API anropas av React - varje POST/PUT skulle kräva en CSRF-token

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Säger till Spring att inte skapa sessioner. Varje request är fristående

                .authorizeHttpRequests(auth -> auth //  requests tillåts utan inloggning. Vem som helst kan anropa vilken endpoint som helst.
                        .anyRequest().permitAll() // allt tillåtet med risken ingen säkerhet kring vem som gör vad
                );

        return http.build();
    }
}