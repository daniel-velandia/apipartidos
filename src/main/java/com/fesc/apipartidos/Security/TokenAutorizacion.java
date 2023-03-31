package com.fesc.apipartidos.Security;

import java.io.IOException;
import java.util.ArrayList;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class TokenAutorizacion extends BasicAuthenticationFilter{

    public TokenAutorizacion(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        String header = request.getHeader(ConstantesSecurity.HEADER_STRING);

        if(header == null || !header.startsWith(ConstantesSecurity.TOKEN_PREFIJO)) {

            chain.doFilter(request, response);

            return;
        }

        UsernamePasswordAuthenticationToken upat = getAuthenticationToken(header);

        SecurityContextHolder.getContext().setAuthentication(upat);

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(String header) {
        
        if (header != null) {

            String token = header.replace(ConstantesSecurity.TOKEN_PREFIJO, "");

            SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(ConstantesSecurity.TOKEN_SECRETO));

            String usuario = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().getSubject();

            if (usuario != null) {

                return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
            }
        }
        return null;
    }
    
}
