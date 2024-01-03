package com.airport.airport.security;

import com.airport.airport.entity.Airline;
import com.airport.airport.entity.RefreshToken;
import com.airport.airport.entity.User;
import com.airport.airport.exception.InvalidCredentialsException;
import com.airport.airport.exception.ResourceNotFoundException;
import com.airport.airport.exception.TokenRefreshException;
import com.airport.airport.payload.*;
import com.airport.airport.repository.AirlineRepository;
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
    private final AirlineRepository airlineRepository;

    public AuthenticationResponse register(SignupDto signupDto) {

        Airline airline = null;

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

        if (signupDto.getAirlineId() != null) {
            airline = airlineRepository.findById(signupDto.getAirlineId())
                    .orElseThrow(() -> new ResourceNotFoundException("Airline", "id", signupDto.getAirlineId()));
        }

        User user = User.builder()
                .name(signupDto.getName())
                .email(signupDto.getEmail())
                .username(signupDto.getUsername())
                .password(passwordEncoder.encode(signupDto.getPassword()))
                .role(signupDto.getRole())
                .airline(airline)
                .build();

        userRepository.save(user);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return AuthenticationResponse.builder()
                .token(jwtService.generateToken(user))
                .refreshToken(refreshToken.getToken())
                .role(signupDto.getRole())
                .airlineId(signupDto.getAirlineId())
                .build();

    }

    public JWTAuthResponse authenticate(LoginDto loginDto) {
        JWTAuthResponse jwtResponse;
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

        if (!user.getRole().name().equals("AIRLINE")) {
            if (refreshToken == null) {
                refreshToken = refreshTokenService.createRefreshToken(user.getId());
            }
            jwtResponse = new JWTAuthResponse(jwtToken, refreshToken.getToken(), user.getRole(), null);
        } else {
            if (refreshToken == null) {
                refreshToken = refreshTokenService.createRefreshToken(user.getId());
            }
            jwtResponse = new JWTAuthResponse(jwtToken, refreshToken.getToken(), user.getRole(), user.getAirline().getId());
        }
        return jwtResponse;
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
