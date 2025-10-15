package com.kanionland.user.bff.infrastructure.responses;

import com.kanionland.user.bff.domain.entities.User;

public record JWTResponse(String token, String type, Long id, String username, String email) {
  public static JWTResponse of(String token, User user) {
    return new JWTResponse(
        token,
        "Bearer",
        user.getId(),
        user.getUsername(),
        user.getEmail()
    );
  }
}