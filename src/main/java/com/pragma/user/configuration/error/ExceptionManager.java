package com.pragma.user.configuration.error;

import com.pragma.user.configuration.Constants;
import com.pragma.user.configuration.error.dto.ExceptionArgumentResponse;
import com.pragma.user.configuration.error.dto.ExceptionResponse;
import com.pragma.user.domain.exception.AlreadyExistException;
import com.pragma.user.domain.exception.InitialEndpointUnavailableException;
import com.pragma.user.domain.exception.WithoutPermitsException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@AllArgsConstructor
@ControllerAdvice
public class ExceptionManager {

	public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	public static ResponseEntity<ExceptionResponse> generalExceptionHandler(String exceptionMessage, HttpStatus httpStatus) {

		ExceptionResponse response = new ExceptionResponse(
				FORMATTER.format(LocalDateTime.now()),
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

	@ExceptionHandler(AlreadyExistException.class)
	public ResponseEntity<ExceptionResponse> handlerAlreadyExistException(AlreadyExistException exception) {
		return generalExceptionHandler(exception.getMessage(), HttpStatus.CONFLICT);
	}

	@ExceptionHandler(WithoutPermitsException.class)
	public ResponseEntity<ExceptionResponse> handlerWithoutPermitsException(WithoutPermitsException exception) {
		return generalExceptionHandler(exception.getMessage(), HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler(InitialEndpointUnavailableException.class)
	public ResponseEntity<ExceptionResponse> handlerInitialEndpointUnavailableException(InitialEndpointUnavailableException exception) {
		return generalExceptionHandler(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}


}
