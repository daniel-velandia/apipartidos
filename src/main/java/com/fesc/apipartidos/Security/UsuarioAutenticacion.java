package com.fesc.apipartidos.Security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fesc.apipartidos.Models.Peticiones.UsuarioSigupRequestModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UsuarioAutenticacion extends UsernamePasswordAuthenticationFilter{
    
    private final AuthenticationManager authenticationManager;

    public UsuarioAutenticacion(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        
                try {
                    
                    UsuarioSigupRequestModel usuarioSigupRequestModel = new ObjectMapper().readValue(
                        request.getInputStream(), UsuarioSigupRequestModel.class);

                    UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(
                        usuarioSigupRequestModel.getUsername(), 
                        usuarioSigupRequestModel.getPassword(),
                        new ArrayList<>());

                    Authentication authentication = authenticationManager.authenticate(upat);

                    return authentication;

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        
        String username = ((User) authResult.getPrincipal()).getUsername();

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ConstantesSecurity.TOKEN_SECRETO));

        String token = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + ConstantesSecurity.FECHA_EXPIRACION))
                .signWith(key)
                .compact();
        
        response.addHeader(ConstantesSecurity.HEADER_STRING, ConstantesSecurity.TOKEN_PREFIJO + token);
    }
}
