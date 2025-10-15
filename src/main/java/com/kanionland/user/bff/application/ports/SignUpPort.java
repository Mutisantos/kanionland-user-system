package com.kanionland.user.bff.application.ports;

import com.kanionland.user.bff.domain.commands.SignUpCommand;

public interface SignUpPort {
  void registerSignUp(SignUpCommand request);
}