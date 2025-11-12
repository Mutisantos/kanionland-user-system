package com.kanionland.user.bff.infrastructure.security;

import com.kanionland.user.bff.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
// Implements Spring Security Interface to load user details
// Authentication providers managers will inject it to authenticate username and password
public class UserDetailsServiceAdapter implements UserDetailsService {

  private final UserRepository userRepository;

  @Override
  //Method called by the org.springframework.security.authentication.dao.DaoAuthenticationProvider
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .orElseThrow(() ->
            new UsernameNotFoundException("User not found with username: " + username)
        );
  }

}
