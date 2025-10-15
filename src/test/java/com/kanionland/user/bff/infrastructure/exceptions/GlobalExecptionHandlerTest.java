package com.kanionland.user.bff.infrastructure.exceptions;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;

class GlobalExceptionHandlerTest {

  private GlobalControllerAdvice exceptionHandler;

  @BeforeEach
  void setUp() {
    exceptionHandler = new GlobalControllerAdvice();
  }

  @Test
  void whenHandleMethodArgumentNotValid_thenReturnsBadRequest() {
    // Given
    BindingResult bindingResult = mock(BindingResult.class);
    FieldError fieldError = new FieldError("object", "field", "defaultMessage");
    when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(fieldError));

    MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
    when(ex.getBindingResult()).thenReturn(bindingResult);

    // When
    Map<String, String> response = exceptionHandler.handleValidationExceptions(ex);

    // Then
    assertThat(response).hasSize(1);
    assertThat(response).containsKey("field");
    assertThat(response.get("field")).isEqualTo("defaultMessage");
  }

  @Test
  void whenHandleResponseStatusException_thenReturnsCorrectStatus() {
    // Given
    ResponseStatusException ex = new ResponseStatusException(
        HttpStatus.BAD_REQUEST, "Error message"
    );

    // When
    ResponseEntity<Map<String, String>> response =
        exceptionHandler.handleResponseStatusException(ex);

    // Then
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).containsKey("message");
    assertThat(response.getBody().get("message")).isEqualTo("Error message");
  }
}