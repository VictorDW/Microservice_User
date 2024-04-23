package com.pragma.user.domain.exception;

public class WithoutPermitsException extends RuntimeException {

  public WithoutPermitsException(String message) {
    super(message);
  }
}
