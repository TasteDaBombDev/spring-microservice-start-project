package com.postme.authService.controllers;

import com.postme.authService.models.requests.LoginRequest;
import com.postme.authService.models.requests.RegisterRequest;
import com.postme.authService.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/v1/auth")
@CrossOrigin(origins = {"http://localhost:3000"}, allowCredentials = "true")
public class AuthController {

    @Autowired
    public AuthService authService;

    @GetMapping("/")
    public String onAppOpened() {
        return "Whoo! You dig a lot to get here!";
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest signUpRequest) {
        return authService.register(signUpRequest);
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser() {
        return authService.logout();
    }

}