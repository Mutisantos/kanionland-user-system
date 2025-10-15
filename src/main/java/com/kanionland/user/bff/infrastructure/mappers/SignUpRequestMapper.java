package com.kanionland.user.bff.infrastructure.mappers;

import com.kanionland.user.bff.domain.commands.SignUpCommand;
import com.kanionland.user.bff.infrastructure.requests.SignUpRequest;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface SignUpRequestMapper {

  SignUpRequestMapper INSTANCE = Mappers.getMapper(SignUpRequestMapper.class);

  SignUpCommand toSignUpCommand(SignUpRequest request);

}
