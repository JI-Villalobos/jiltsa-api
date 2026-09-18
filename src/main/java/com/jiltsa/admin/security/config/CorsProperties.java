package com.jiltsa.admin.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;

/**
 * {@code app.cors.allowed-origins}: comma-separated origin patterns
 * (env APP_CORS_ALLOWED_ORIGINS, e.g. https://admin.example.com,https://*.example.com).
 * Defaults to every origin.
 */
@ConfigurationProperties("app.cors")
public record CorsProperties(@DefaultValue("*") List<String> allowedOrigins) {
}
