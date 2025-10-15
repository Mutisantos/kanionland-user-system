package com.kanionland.user.bff.application.adapters;

import com.kanionland.user.bff.application.ports.SignUpPort;
import com.kanionland.user.bff.domain.commands.SignUpCommand;
import com.kanionland.user.bff.domain.entities.User;
import com.kanionland.user.bff.domain.repositories.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class SignUpAdapter implements SignUpPort {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Override
  public void registerSignUp(SignUpCommand request) {
    if (userRepository.existsByEmailOrUsername(request.username(), request.email())) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST,
          "Username or email already exists"
      );
    }

    User user = new User();
    user.setUsername(request.username());
    user.setEmail(request.email());
    user.setPassword(passwordEncoder.encode(request.password()));
    user.setRole("USER");
    user.setActive(true);
    user.setCreatedAt(LocalDateTime.now());
    user.setLastAccessAt(LocalDateTime.now());

    userRepository.save(user);
  }

}
