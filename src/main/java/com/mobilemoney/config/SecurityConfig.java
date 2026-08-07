package com.mobilemoney.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http)
            throws Exception {

        http

            .authorizeHttpRequests(auth -> auth
//toute les pages sont accesible sans autorisation
                    .anyRequest().permitAll()

            )

            //desactive la protection CSRF
            .csrf(csrf -> csrf.disable())
//supprime la page de connexion
            .formLogin(login -> login.disable());

        return http.build();
    }

}