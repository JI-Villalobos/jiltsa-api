package com.jiltsa.admin.security.auth;
public record AuthenticationResponse(String token, Integer branchId, String role) {
}
