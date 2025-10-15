package com.kanionland.user.bff.infrastructure.controllers;

import com.kanionland.user.bff.application.ports.SignUpPort;
import com.kanionland.user.bff.application.ports.UserLogInPort;
import com.kanionland.user.bff.domain.commands.LogInCommand;
import com.kanionland.user.bff.infrastructure.mappers.LogInRequestMapper;
import com.kanionland.user.bff.infrastructure.mappers.SignUpRequestMapper;
import com.kanionland.user.bff.infrastructure.requests.LogInRequest;
import com.kanionland.user.bff.infrastructure.requests.SignUpRequest;
import com.kanionland.user.bff.infrastructure.responses.JWTResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final SignUpPort signUpPort;
  private final UserLogInPort userLogInPort;
  private final SignUpRequestMapper signUpRequestMapper;
  private final LogInRequestMapper loginRequestMapper;

  @PostMapping("/sign-up")
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(@Valid @RequestBody SignUpRequest request) {
    signUpPort.registerSignUp(signUpRequestMapper.toSignUpCommand(request));
  }


  @PostMapping("/log-in")
  public ResponseEntity<JWTResponse> logIn(@Valid @RequestBody LogInRequest loginRequest) {
    final LogInCommand logInCommand = loginRequestMapper.toSignUpCommand(loginRequest);
    return ResponseEntity.ok(userLogInPort.userLogIn(logInCommand));
  }


}
