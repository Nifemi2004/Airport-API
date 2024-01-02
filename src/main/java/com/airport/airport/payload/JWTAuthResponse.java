package com.airport.airport.payload;

import com.airport.airport.utils.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JWTAuthResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private String refreshToken;
    private Role role;
    private Long airlineId;

    public JWTAuthResponse(String accessToken, String refreshToken, Role role, Long airlineId) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
        this.airlineId = airlineId;
    }
}
