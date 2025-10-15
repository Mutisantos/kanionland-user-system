package com.kanionland.user.bff.domain.commands;

public record SignUpCommand(
    String username,
    String email,
    String password
) {

}
