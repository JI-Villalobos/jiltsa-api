package com.jiltsa.admin.security.config;

import com.jiltsa.admin.security.service.JWTService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
           @NonNull HttpServletRequest request,
           @NonNull HttpServletResponse response,
           @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String token;

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }
        token = authHeader.substring(7);
        if (SecurityContextHolder.getContext().getAuthentication() == null){
            authenticate(token, request);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * Sets the security context if the token is valid. A malformed, expired or
     * orphaned token leaves the request unauthenticated so the entry point
     * answers 401/403 instead of the filter chain failing with a 500.
     */
    private void authenticate(String token, HttpServletRequest request) {
        try {
            String email = jwtService.extractUsername(token);
            if (email == null) {
                return;
            }
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
            if (jwtService.isTokenValid(token, userDetails)){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (JwtException | IllegalArgumentException | UsernameNotFoundException ex) {
            SecurityContextHolder.clearContext();
        }
    }
}
