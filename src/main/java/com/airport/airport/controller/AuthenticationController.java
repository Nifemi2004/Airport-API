package com.airport.airport.controller;

import com.airport.airport.exception.GlobalExceptionHandler;
import com.airport.airport.payload.AuthenticationResponse;
import com.airport.airport.payload.JWTAuthResponse;
import com.airport.airport.payload.LoginDto;
import com.airport.airport.payload.SignupDto;
import com.airport.airport.security.AuthenticationService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody SignupDto signupDto
            ){
            return ResponseEntity.ok(service.register(signupDto));
    }

    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(
            @RequestBody LoginDto loginDto
    ){
        return ResponseEntity.ok(service.authenticate(loginDto));
    }
}
