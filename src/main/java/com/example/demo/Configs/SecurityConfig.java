package com.example.demo.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{




        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/Admin/**").hasRole("ADMIN")
                        .requestMatchers("/h2/**").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2/**")
                        .ignoringRequestMatchers("/Admin/generate-single/**")
                        .ignoringRequestMatchers("/Admin/generate-multiple/**")
                        .ignoringRequestMatchers("/Admin/List/**")
                )
                .headers(headers -> headers
                        .frameOptions().sameOrigin()
                )

                .formLogin(Customizer.withDefaults())
                .build();

    }
}
