package com.kanionland.user.bff.infrastructure.responses;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpResponse(
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
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