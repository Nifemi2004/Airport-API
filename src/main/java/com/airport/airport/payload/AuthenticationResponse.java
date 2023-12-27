package com.airport.airport.payload;

import com.airport.airport.entity.RefreshToken;
import com.airport.airport.utils.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {
    private String token;

    private String refreshToken;

    @Enumerated(EnumType.STRING)
    private Role role;
}
