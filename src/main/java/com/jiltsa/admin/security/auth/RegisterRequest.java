package com.jiltsa.admin.security.auth;

public record RegisterRequest(String username, String email, String pass) {
}
