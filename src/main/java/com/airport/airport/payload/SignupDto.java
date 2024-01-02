package com.airport.airport.payload;

import com.airport.airport.utils.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SignupDto {

    @NotEmpty(message = "cannot be empty")
    @Size(min = 3)
    private String name;

    @NotEmpty(message = "cannot be empty")
    @Size(min = 5)
    private String username;

    @NotEmpty(message = "cannot be empty")
    @Email
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    private Long airlineId;
}
