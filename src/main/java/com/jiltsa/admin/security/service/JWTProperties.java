package com.jiltsa.admin.security.service;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;

/**
 * {@code jwt.key}: base64-encoded HMAC key of at least 256 bits (env JWT_KEY, required).
 * {@code jwt.expiration}: token lifetime, e.g. 12h, 30m (env JWT_EXPIRATION, default 12 hours).
 * <p>
 * The token carries no revocation data, so its lifetime is the window a leaked token
 * stays usable: keep it around one working day, not weeks.
 */
@Validated
@ConfigurationProperties("jwt")
public record JWTProperties(@NotBlank String key, @DefaultValue("12h") Duration expiration) {
}
