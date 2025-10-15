package com.kanionland.user.bff.application.ports;

import com.kanionland.user.bff.infrastructure.requests.LogInRequest;
import com.kanionland.user.bff.infrastructure.responses.JWTResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserLogInPort extends UserDetailsService {
  JWTResponse authenticateUser(LogInRequest loginRequest);
}
