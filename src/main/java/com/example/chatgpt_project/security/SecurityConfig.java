package com.example.chatgpt_project.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/api/pets", "/api/households", "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/pets", "/api/households", "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/*/password", "/api/users/*/unlock").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/pets", "/api/households").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/statistics").hasAnyRole("ADMIN", "USER")
                .requestMatchers(HttpMethod.GET, "/api/pets", "/api/households").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic()
                .and()
                .csrf().disable();
    }
}
