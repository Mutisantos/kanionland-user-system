package com.kanionland.user.bff.infrastructure.controllers;

import com.kanionland.user.bff.application.ports.SignUpPort;
import com.kanionland.user.bff.infrastructure.mappers.SignUpRequestMapper;
import com.kanionland.user.bff.infrastructure.requests.SignUpRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
  private final SignUpRequestMapper signUpRequestMapper;

  @PostMapping("/sign-up")
  @ResponseStatus(HttpStatus.CREATED)
  public void signUp(@Valid @RequestBody SignUpRequest request) {
    signUpPort.registerSignUp(signUpRequestMapper.toSignUpCommand(request));
  }
}
