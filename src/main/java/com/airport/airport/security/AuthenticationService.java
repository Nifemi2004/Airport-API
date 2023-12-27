package com.airport.airport.security;

import com.airport.airport.entity.RefreshToken;
import com.airport.airport.entity.User;
import com.airport.airport.exception.InvalidCredentialsException;
import com.airport.airport.exception.TokenRefreshException;
import com.airport.airport.payload.*;
import com.airport.airport.repository.RefreshTokenRepository;
import com.airport.airport.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;

    public AuthenticationResponse register(SignupDto signupDto) {

        if(userRepository.existsByUsername(signupDto.getUsername())){
            return  AuthenticationResponse.builder()
                    .token("This username already exists")
                    .build();
        }


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

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        var jwtToken = jwtService.generateToken(user);
        return  AuthenticationResponse.builder()
                .token(jwtToken)
                .refreshToken(refreshToken.getToken())
                .role(signupDto.getRole())
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
                .orElseThrow(
                        () -> new InvalidCredentialsException()
                );
        var jwtToken = jwtService.generateToken(user);

        RefreshToken refreshToken = refreshTokenRepository.findByUserId(user.getId());

        if(refreshToken == null) {
            RefreshToken refresh = refreshTokenService.createRefreshToken(user.getId());
            return new JWTAuthResponse(jwtToken, refresh.getToken(), user.getRole());
        }

        return new JWTAuthResponse(jwtToken, refreshToken.getToken(), user.getRole());

    }

    public TokenRefreshResponse refresh(TokenRefreshRequest request){
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    var jwtToken = jwtService.generateToken(user);
                    return new TokenRefreshResponse(jwtToken, requestRefreshToken);
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token has expired!!!"));
    }
}
