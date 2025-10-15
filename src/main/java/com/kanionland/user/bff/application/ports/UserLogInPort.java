package com.kanionland.user.bff.application.ports;

import com.kanionland.user.bff.domain.commands.LogInCommand;
import com.kanionland.user.bff.infrastructure.responses.JWTResponse;

public interface UserLogInPort {

  JWTResponse userLogIn(LogInCommand loginRequest);
}
