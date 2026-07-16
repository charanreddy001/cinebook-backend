package com.charan.cinebook.controller;

import com.charan.cinebook.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest registerRequest) {
        authService
                .register(registerRequest.getName(),registerRequest.getEmail(),registerRequest.getPassword());

        return "Registered Successfully";
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return authService
                .login(loginRequest.getEmail(),loginRequest.getPassword());
    }
}
