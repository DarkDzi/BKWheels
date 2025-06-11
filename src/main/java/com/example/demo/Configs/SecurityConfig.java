package com.example.demo.Configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{




         http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/AdminMenu/**").hasRole("ADMIN")
                        .requestMatchers("/h2/**").permitAll()
                        .anyRequest().permitAll()
                )
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2/**")
                        .ignoringRequestMatchers("/QrGenerate/generate-single/**")
                        .ignoringRequestMatchers("/QrGenerate/generate-multiple/**")
                        .ignoringRequestMatchers("/QrGenerate/List/**")
                )
                .headers(headers -> headers
                        .frameOptions().sameOrigin()
                )

                .formLogin(form -> form
                        .defaultSuccessUrl("/AdminMenu", true)
                        .permitAll()
                )

                .logout(Customizer.withDefaults());

        return http.build();

    }

}
