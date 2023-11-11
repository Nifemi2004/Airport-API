package com.airport.airport.payload;

import com.airport.airport.utils.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class SignupDto {
    private String name;
    private String username;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
