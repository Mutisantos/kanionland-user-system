package com.kanionland.user.bff.infrastructure.mappers;

import com.kanionland.user.bff.domain.commands.LogInCommand;
import com.kanionland.user.bff.infrastructure.requests.LogInRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface LogInRequestMapper {

  LogInRequestMapper INSTANCE = Mappers.getMapper(LogInRequestMapper.class);

  LogInCommand toSignUpCommand(LogInRequest request);

}
