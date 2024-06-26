package com.imdb.imdb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) {
        try {
            return httpSecurity
                    .csrf(csrf -> csrf.disable())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/auth/deactivate").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.POST, "/api/auth/activate").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.GET, "/api/filmes").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/filmes/{id}").permitAll()
                            .requestMatchers(HttpMethod.GET, "/api/filmes/search").permitAll()
                            .requestMatchers(HttpMethod.POST, "/api/filmes").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.PUT, "/api/filmes").hasRole("ADMIN")
                            .requestMatchers(HttpMethod.DELETE, "/api/filmes").hasRole("ADMIN")
                            .anyRequest().authenticated()
                    )
                    .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
