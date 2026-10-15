package com.jiltsa.admin.security.controller;

import jakarta.validation.Valid;
import com.jiltsa.admin.security.auth.AuthenticationRequest;
import com.jiltsa.admin.security.auth.AuthenticationResponse;
import com.jiltsa.admin.security.auth.RegisterRequest;
import com.jiltsa.admin.security.service.AuthenticationService;
import com.jiltsa.admin.security.AdminOnly;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jiltsa/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService service;

    @Operation(summary = "Register a user", description = "Returns a token for the new user.")
    @AdminOnly
    @PostMapping("/register")
    public AuthenticationResponse register(@Valid @RequestBody RegisterRequest request){
        return service.register(request);
    }

    @Operation(summary = "Log in", description = "Returns the bearer token to send on every other request, with the user's branch and role.")
    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@Valid @RequestBody AuthenticationRequest request){
        return service.authenticate(request);
    }
}
