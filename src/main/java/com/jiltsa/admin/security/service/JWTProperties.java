package com.jiltsa.admin.security.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("jwt")
public record JWTProperties(String key) {
}
