package com.EcharityPlatform.MfarijiPlatform.security;

import com.EcharityPlatform.MfarijiPlatform.Entity.Widow;
import com.EcharityPlatform.MfarijiPlatform.repository.WidowRepository;
import com.EcharityPlatform.MfarijiPlatform.service.JWTService;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final WidowRepository widowRepository;

    public JWTRequestFilter(JWTService jwtService, WidowRepository widowRepository) {
        this.jwtService = jwtService;
        this.widowRepository = widowRepository;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            String token = tokenHeader.substring(7);
            try {
                String username = jwtService.getUsername(token);
                Optional<Object> opWidow = widowRepository.findByUsernameIgnoreCase(username);
                if (opWidow.isPresent()) {
                    Widow widow = (Widow) opWidow.get();
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(widow, null, new ArrayList());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (JWTDecodeException ex) {
            }
        }
        filterChain.doFilter(request, response);
    }

}
