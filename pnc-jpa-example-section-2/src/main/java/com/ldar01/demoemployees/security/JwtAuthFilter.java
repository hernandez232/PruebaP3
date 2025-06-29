package com.ldar01.demoemployees.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    /*
    La clase JwtAuthFilter es un filtro personalizado que extiende OncePerRequestFilter de Spring Security.

    Su propósito principal es validar el token JWT incluido en las solicitudes HTTP entrantes y construir un objeto de
    autenticación válido para String Security.

    Esto permite que el sistema reconozca al usuario autenticado basado en el token.
    */
    private JwtTokenProvider jwtTokenProvider;
    private CustomUserDetailService customUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Extract the JWT token from the Authorization header of the request
        String token = getTokenFromRequest(request);

        // Validate the token and proceed if it's valid
        if (token != null && jwtTokenProvider.validateToken(token)) {
            // Retrieve the username embedded in the token
            String username = jwtTokenProvider.getUsernameFromToken(token);

            // Load user details from the database or user service
            UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

            // Create an authentication object with the user's details and authorities
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            // Attach additional details about the request to the authentication object
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // Set the authentication object in the SecurityContext for Spring Security
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // Continue the filter chain to process the next filter or the request itself
        filterChain.doFilter(request, response);

    }

    // Crear una función que ayude a obtener el token por el request
    private String getTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7, bearerToken.length()); // Eliminar "Bearer " del inicio del token
        }
        return null; // Si no hay token, retornar null
    }
}
