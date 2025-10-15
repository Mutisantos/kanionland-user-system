package com.kanionland.user.bff.application.adapters;

import com.kanionland.user.bff.application.ports.UserLogInPort;
import com.kanionland.user.bff.domain.commands.LogInCommand;
import com.kanionland.user.bff.domain.entities.User;
import com.kanionland.user.bff.domain.repositories.UserRepository;
import com.kanionland.user.bff.infrastructure.responses.JWTResponse;
import com.kanionland.user.bff.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserLogInAdapter implements UserLogInPort {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;
  private final UserRepository userRepository;

  @Override
  public JWTResponse userLogIn(LogInCommand loginRequest) throws UsernameNotFoundException {
    final Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.username(),
            loginRequest.password()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    final User user = userRepository.findByUsername(loginRequest.username())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    final String jwt = tokenProvider.generateToken(authentication.getName(), user.getRole());
    return JWTResponse.of(jwt, user);
  }
}
