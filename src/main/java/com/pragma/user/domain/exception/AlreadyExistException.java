package com.pragma.user.domain.exception;

public class AlreadyExistException extends RuntimeException {

	public AlreadyExistException(String message) {
		super(message);
	}
}
