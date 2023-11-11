package com.airport.airport.security;

import com.airport.airport.entity.User;
import com.airport.airport.payload.AuthenticationResponse;
import com.airport.airport.payload.JWTAuthResponse;
import com.airport.airport.payload.LoginDto;
import com.airport.airport.payload.SignupDto;
import com.airport.airport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(SignupDto signupDto) {

        if(userRepository.existsByUsername(signupDto.getUsername())){
            return  AuthenticationResponse.builder()
                    .token("This username already exists")
                    .build();
        }

        //add check for email

        if(userRepository.existsByEmail(signupDto.getEmail())){
            return  AuthenticationResponse.builder()
                    .token("This email already exists")
                    .build();
        }

        var user = User.builder()
                .name(signupDto.getName())
                .email(signupDto.getEmail())
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .role(signupDto.getRole())
                .build();

        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }

    public JWTAuthResponse authenticate(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        var user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return new JWTAuthResponse(jwtToken);

    }
}
