package com.fesc.apipartidos.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.fesc.apipartidos.Services.IUsuarioService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    
    private final IUsuarioService iUsuarioService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfiguration(IUsuarioService iUsuarioService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.iUsuarioService = iUsuarioService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {

        return http
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
            .requestMatchers(HttpMethod.GET, "/partido").permitAll()
            .requestMatchers(HttpMethod.GET, "/partido/{id}").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(new UsuarioAutenticacion(authenticationManager))
            .build();
        
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        // authenticationManager.userDetailsService(iUsuarioService).passwordEncoder(bCryptPasswordEncoder);
        // return authenticationManager;

        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .userDetailsService(iUsuarioService)
            .passwordEncoder(bCryptPasswordEncoder)
            .and()
            .build();
    }
}
