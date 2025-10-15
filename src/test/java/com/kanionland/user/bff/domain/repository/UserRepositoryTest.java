package com.kanionland.user.bff.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.kanionland.user.bff.domain.entities.User;
import com.kanionland.user.bff.domain.repositories.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

  public static final String TEST_MAIL = "testuno@correo.co";
  public static final String TEST_USER = "testuser";
  public static final String TEST_PASSWORD = "password";
  @Autowired
  private UserRepository userRepository;

  @BeforeEach
  void setUp() {
    userRepository.deleteAll();
  }

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  void whenSaveUser_thenFindByUsername() {
    // Given
    User user = new User();
    user.setUsername(TEST_USER);
    user.setPassword(TEST_PASSWORD);
    user.setEmail(TEST_MAIL);
    user.setRole("USER");
    user.setCreatedAt(LocalDateTime.now());
    user.setActive(true);

    // When
    userRepository.save(user);
    Optional<User> foundUserByUsername = userRepository.findByUsername(TEST_USER);
    Optional<User> foundUserByEmail = userRepository.findByEmail(TEST_MAIL);

    // Then
    assertThat(foundUserByUsername).isPresent();
    assertThat(foundUserByUsername.get().getUsername()).isEqualTo(TEST_USER);
    assertThat(foundUserByEmail).isPresent();
    assertThat(foundUserByEmail.get().getUsername()).isEqualTo(TEST_USER);
  }

  @ParameterizedTest
  @MethodSource("provideParameters")
  void whenExistsByUsernameAndEmail_thenReturnTrue(String email, String username) {
    // Given
    User user = new User();
    user.setUsername(TEST_USER);
    user.setPassword(TEST_PASSWORD);
    user.setEmail(TEST_MAIL);
    user.setRole("USER");
    user.setCreatedAt(LocalDateTime.now());
    user.setActive(true);
    // When
    userRepository.save(user);
    // Then
    assertThat(userRepository.existsByEmailOrUsername(email, username)).isTrue();
  }

  @Test
  void whenExistsByUsernameAndEmail_andNoUserFound_thenReturnFalse() {
    // Given
    User user = new User();
    user.setUsername(TEST_USER);
    user.setPassword(TEST_PASSWORD);
    user.setEmail(TEST_MAIL);
    user.setRole("USER");
    user.setCreatedAt(LocalDateTime.now());
    user.setActive(true);
    // When
    userRepository.save(user);
    // Then
    assertThat(userRepository.existsByEmailOrUsername("other@mail.com", "user_b")).isFalse();
  }

  private static Stream<Arguments> provideParameters() {
    return Stream.of(
        Arguments.of(TEST_MAIL, TEST_USER),
        Arguments.of(TEST_MAIL, "user_b"),
        Arguments.of("other@mail.com", TEST_USER)
    );
  }


}
