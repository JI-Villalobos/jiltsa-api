package com.jiltsa.admin.security.service;

import com.jiltsa.admin.security.auth.AuthenticationRequest;
import com.jiltsa.admin.security.auth.AuthenticationResponse;
import com.jiltsa.admin.security.auth.RegisterRequest;
import com.jiltsa.admin.user.AppUser;
import com.jiltsa.admin.user.AppUserRepository;
import com.jiltsa.admin.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AppUserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request){
        AppUser user = AppUser.builder()
                .username(request.username())
                .email(request.email())
                .pass(passwordEncoder.encode(request.pass()))
                .branchId(request.branchId())
                .role(Role.USER)
                .build();
        repository.save(user);
        return getAuthenticationResponse(user);

    }

    private AuthenticationResponse getAuthenticationResponse(AppUser user) {
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token);
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request){
        authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
                  request.email(),
                  request.pass()
          )
        );
        AppUser user = repository.findByEmail(request.email())
                .orElseThrow();
        return getAuthenticationResponse(user);
    }
}
