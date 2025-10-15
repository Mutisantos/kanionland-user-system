package com.kanionland.user.bff.infrastructure.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kanionland.user.bff.application.adapters.UserDetailsServiceAdapter;
import com.kanionland.user.bff.application.ports.SignUpPort;
import com.kanionland.user.bff.application.ports.UserLogInPort;
import com.kanionland.user.bff.domain.commands.SignUpCommand;
import com.kanionland.user.bff.domain.repositories.UserRepository;
import com.kanionland.user.bff.infrastructure.config.SecurityConfig;
import com.kanionland.user.bff.infrastructure.mappers.LogInRequestMapper;
import com.kanionland.user.bff.infrastructure.mappers.SignUpRequestMapper;
import com.kanionland.user.bff.infrastructure.requests.SignUpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

@WebMvcTest(UserController.class)
@ContextConfiguration(classes = {SecurityConfig.class, UserController.class,
    UserDetailsServiceAdapter.class})
@ActiveProfiles("test")
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockitoBean
  private SignUpPort signUpPort;

  @MockitoBean
  private SignUpRequestMapper signUpRequestMapper;

  @MockitoBean
  private LogInRequestMapper logInRequestMapper;

  @MockitoBean
  private UserLogInPort userLogInPort;

  @MockitoBean
  private UserRepository userRepository;

  @Test
  void whenValidInput_thenReturns201() throws Exception {

    // Given
    SignUpRequest request = new SignUpRequest("testuser", "test@example.com", "SAfePassword12!");
    doNothing().when(signUpPort).registerSignUp(any(SignUpCommand.class));
    when(signUpRequestMapper.toSignUpCommand(any(SignUpRequest.class)))
        .thenReturn(new SignUpCommand("testuser", "test@example.com", "SAfePassword12!"));

    // When & Then
    mockMvc.perform(post("/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated());
  }

  @Test
  void whenInvalidEmail_thenReturns400() throws Exception {
    // Given
    SignUpRequest request = new SignUpRequest("testuser", "invalid-email", "Password123");

    // When & Then
    mockMvc.perform(post("/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void whenWeakPassword_thenReturns400() throws Exception {
    // Given
    SignUpRequest request = new SignUpRequest("testuser", "test@example.com", "weak");

    // When & Then
    mockMvc.perform(post("/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }

  @Test
  void whenDuplicateUser_thenReturns400() throws Exception {
    // Given
    SignUpRequest request = new SignUpRequest("existinguser", "test@example.com", "Password123");
    when(signUpRequestMapper.toSignUpCommand(any(SignUpRequest.class)))
        .thenReturn(new SignUpCommand("testuser", "test@example.com", "SAfePassword12!"));
    doThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username or email already exists"))
        .when(signUpPort).registerSignUp(any(SignUpCommand.class));

    // When & Then
    mockMvc.perform(post("/users/sign-up")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isBadRequest());
  }
}