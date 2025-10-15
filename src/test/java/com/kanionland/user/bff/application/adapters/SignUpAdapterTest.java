package com.kanionland.user.bff.application.adapters;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kanionland.user.bff.domain.commands.SignUpCommand;
import com.kanionland.user.bff.domain.entities.User;
import com.kanionland.user.bff.domain.repositories.UserRepository;
import com.kanionland.user.bff.infrastructure.requests.SignUpRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

@ExtendWith(MockitoExtension.class)
class SignUpAdapterTest {

  public static final String USERNAME = "newuser";
  public static final String EMAIL = "existing@example.com";
  public static final String PASSWORD = "Password123";
  @Mock
  private UserRepository userRepository;

  @Mock
  private PasswordEncoder passwordEncoder;

  @InjectMocks
  private SignUpAdapter signUpAdapter;

  @Captor
  private ArgumentCaptor<User> userCaptor;

  private final String encodedPassword = "encodedPassword";

  @Test
  void whenRegisterNewUser_thenSaveUser() {
    // Given
    SignUpCommand request = new SignUpCommand(USERNAME, EMAIL, PASSWORD);
    when(userRepository.existsByEmailOrUsername(EMAIL, USERNAME)).thenReturn(false);
    when(userRepository.save(any(User.class))).thenReturn(new User());
    when(passwordEncoder.encode(any())).thenReturn(encodedPassword);
    // When
    signUpAdapter.registerSignUp(request);

    // Then
    verify(userRepository).save(userCaptor.capture());
    User savedUser = userCaptor.getValue();

    assertThat(savedUser.getUsername()).isEqualTo(USERNAME);
    assertThat(savedUser.getEmail()).isEqualTo(EMAIL);
    assertThat(savedUser.getPassword()).isEqualTo(encodedPassword);
    assertThat(savedUser.getRole()).isEqualTo("USER");
    assertThat(savedUser.isActive()).isTrue();
  }

  @Test
  void whenRegisterExistingUser_thenThrowException() {
    // Given
    SignUpCommand request = new SignUpCommand(USERNAME, EMAIL, PASSWORD);
    when(userRepository.existsByEmailOrUsername(EMAIL, USERNAME)).thenReturn(true);
    // When | Then
    assertThatThrownBy(() -> signUpAdapter.registerSignUp(request))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessageContaining("Username or email already exists");
    verify(userRepository, never()).save(any());
  }
}