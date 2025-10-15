package com.kanionland.user.bff.infrastructure.requests;

import jakarta.validation.constraints.NotBlank;

public record LogInRequest(
    @NotBlank String username,
    @NotBlank String password
) {

}