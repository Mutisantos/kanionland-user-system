package com.kanionland.user.bff.application.adapters;

import com.kanionland.user.bff.application.ports.UserLogInPort;
import com.kanionland.user.bff.domain.entities.User;
import com.kanionland.user.bff.domain.repositories.UserRepository;
import com.kanionland.user.bff.infrastructure.requests.LogInRequest;
import com.kanionland.user.bff.infrastructure.responses.JWTResponse;
import com.kanionland.user.bff.infrastructure.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() ->
            new UsernameNotFoundException("User not found with username: " + username)
        );
  }

  @Override
  public JWTResponse authenticateUser(LogInRequest loginRequest) throws UsernameNotFoundException {
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.username(),
            loginRequest.password()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.generateToken(authentication.getName());
    User user = userRepository.findByUsername(loginRequest.username())
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    return JWTResponse.of(jwt, user);
  }
}
