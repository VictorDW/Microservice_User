package com.pragma.user.configuration.error;

import com.pragma.user.configuration.Constants;
import com.pragma.user.configuration.error.dto.ExceptionArgumentResponse;
import com.pragma.user.configuration.error.dto.ExceptionResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ExceptionManager {

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	private ResponseEntity<ExceptionResponse> generalExceptionHandler(String exceptionMessage, HttpStatus httpStatus) {

		ExceptionResponse response = new ExceptionResponse(
				formatter.format(LocalDateTime.now()),
				httpStatus.value(),
				httpStatus.getReasonPhrase(),
				exceptionMessage
		);

		return new ResponseEntity<>(response, httpStatus);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<List<ExceptionArgumentResponse>> handlerArgumentInvalidException(MethodArgumentNotValidException exception) {

		var errors = exception.getFieldErrors()
				.stream()
				.map(ExceptionArgumentResponse::new)
				.toList();

		return ResponseEntity.badRequest().body(errors);
	}

	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ExceptionResponse> handlerBadCredential() {
		return generalExceptionHandler(Constants.BAD_CREDENTIALS_MESSAGE, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ExceptionResponse> handlerException(AccessDeniedException exception) {
		return generalExceptionHandler(exception.getMessage(), HttpStatus.FORBIDDEN);
	}
}
