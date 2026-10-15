package com.jiltsa.admin.security.auth;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(@NotBlank String email, @NotBlank String pass) {
}
