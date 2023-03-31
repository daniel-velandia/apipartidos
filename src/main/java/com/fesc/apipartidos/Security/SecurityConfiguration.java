package com.fesc.apipartidos.Security;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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
            .cors()
            .and()
            .csrf().disable()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/usuario").permitAll()
            .requestMatchers(HttpMethod.GET, "/partido").permitAll()
            .requestMatchers(HttpMethod.GET, "/partido/{id}").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(getFiltroAutenticacion(authenticationManager))
            .addFilter(new TokenAutorizacion(authenticationManager))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .build();
        
    }

    public UsuarioAutenticacion getFiltroAutenticacion(AuthenticationManager authenticationManager) throws Exception {
        
        final UsuarioAutenticacion usuarioAutenticacion = new UsuarioAutenticacion(authenticationManager);

        usuarioAutenticacion.setFilterProcessesUrl("/usuario/login");

        return usuarioAutenticacion;

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));

        UrlBasedCorsConfigurationSource configurationSource = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", configuration);

        return configurationSource;
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
