package com.kanionland.user.bff.infrastructure.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
    @Size(min = 6, max = 50, message = "Username must be between 6 and 50 characters")
    String username,

    @Email(message = "Email should be valid")
    String email,

    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
        message = "Password must be at least 8 characters long, contain at least one number, one lowercase and one uppercase letter"
    )
    String password
) {

}