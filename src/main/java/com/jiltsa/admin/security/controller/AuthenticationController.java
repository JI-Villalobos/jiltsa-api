package com.jiltsa.admin.security.controller;

import com.jiltsa.admin.security.auth.AuthenticationRequest;
import com.jiltsa.admin.security.auth.AuthenticationResponse;
import com.jiltsa.admin.security.auth.RegisterRequest;
import com.jiltsa.admin.security.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("jiltsa/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public AuthenticationResponse register(@RequestBody RegisterRequest request){
        return service.register(request);
    }

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest request){
        return service.authenticate(request);
    }
}
