package com.kanionland.user.bff.domain.commands;

public record LogInCommand(
    String username,
    String password
) {

}
