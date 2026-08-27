package com.example.telecom_shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // ==============================
                // CORS
                // ==============================
                .cors(cors -> cors
                        .configurationSource(corsConfigurationSource())
                )

                // ==============================
                // CSRF
                // ==============================
                .csrf(csrf -> csrf.disable())

                // ==============================
                // AUTHORIZATION
                // ==============================
                .authorizeHttpRequests(auth -> auth

                        // API công khai
                        .requestMatchers(
                                "/user/login",
                                "/user/create-account",
                                "/provider/list-provider",
                                "/provider/detail-provider/**"
                        ).permitAll()

                        .requestMatchers(
                                "/user/profile",
                                "/user/logout",
                                "/user/update-account",
                                "/user/update-password"
                        ).permitAll()

                        // Các API khác
                        .anyRequest().authenticated()
                );

        return http.build();
    }


    // ==============================
    // CORS
    // ==============================

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:5173")
        );

        // Cho phép gửi JSESSIONID
        configuration.setAllowCredentials(true);

        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );

        configuration.setAllowedHeaders(
                List.of("*")
        );

        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }
}