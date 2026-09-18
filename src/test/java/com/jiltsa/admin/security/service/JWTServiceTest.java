package com.jiltsa.admin.security.service;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.within;

class JWTServiceTest {
    private static final String KEY = "dGVzdC1rZXktdGVzdC1rZXktdGVzdC1rZXktdGVzdC1rZXk=";
    private final UserDetails user = User.withUsername("diana@jiltsa.mx").password("x").authorities("USER").build();

    @Test
    void tokenLifetimeComesFromTheConfiguredExpiration() {
        JWTService service = new JWTService(new JWTProperties(KEY, Duration.ofHours(12)));

        String token = service.generateToken(user);

        Date expiration = service.extractClaim(token, io.jsonwebtoken.Claims::getExpiration);
        assertThat(expiration).isCloseTo(Date.from(Instant.now().plus(Duration.ofHours(12))), 60_000);
        assertThat(service.isTokenValid(token, user)).isTrue();
        assertThat(service.extractUsername(token)).isEqualTo("diana@jiltsa.mx");
    }

    @Test
    void expiredTokenIsRejected() {
        JWTService service = new JWTService(new JWTProperties(KEY, Duration.ofSeconds(-5)));

        String token = service.generateToken(user);

        assertThatThrownBy(() -> service.extractUsername(token)).isInstanceOf(ExpiredJwtException.class);
    }
}
