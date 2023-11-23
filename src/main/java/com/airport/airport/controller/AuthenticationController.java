package com.airport.airport.controller;

import com.airport.airport.entity.RefreshToken;
import com.airport.airport.exception.GlobalExceptionHandler;
import com.airport.airport.payload.*;
import com.airport.airport.security.AuthenticationService;
import com.airport.airport.security.RefreshTokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/login")
    public ResponseEntity<JWTAuthResponse> authenticate(
            @RequestBody LoginDto loginDto
    ){
        return ResponseEntity.ok(service.authenticate(loginDto));
    }

        @PostMapping("/refreshtoken")
        public ResponseEntity<?> refreshtoken( @RequestBody TokenRefreshRequest request) {
         return ResponseEntity.ok(service.refresh(request));
        }
}
