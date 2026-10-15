package com.jiltsa.admin.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

/**
 * {@code app.cors.allowed-origins}: comma-separated origin patterns
 * (env APP_CORS_ALLOWED_ORIGINS, e.g. https://admin.example.com,https://*.example.com).
 * <p>
 * Defaults to the local Next dev servers. Every deployment sets its own origins: an
 * unset value must fail visibly in the browser, never fall back to allowing all origins.
 */
@ConfigurationProperties("app.cors")
public record CorsProperties(
        @DefaultValue({"http://localhost:3000", "http://localhost:3001"}) List<String> allowedOrigins) {
}
