package com.aditeey.uber.controller;

import com.aditeey.uber.dto.AuthResponse;
import com.aditeey.uber.dto.LoginRequest;
import com.aditeey.uber.dto.RegisterRequest;
import com.aditeey.uber.model.User;
import com.aditeey.uber.repository.UserRepository;
import com.aditeey.uber.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthController(AuthService authService,
                          AuthenticationManager authenticationManager,
                          UserRepository userRepository) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest req) {
        authService.register(req);
        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest req) {

        // Authenticate user (BREAKS CIRCULAR DEPENDENCY)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow();

        String token = authService
                .generateToken(user.getUsername(), user.getRole())
                .get("token");

        return ResponseEntity.ok(new AuthResponse(token));
    }
}