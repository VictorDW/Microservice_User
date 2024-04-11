package com.pragma.user.adapters.driving.dto.request;

import com.pragma.user.configuration.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserAdminRequest(

		@NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
		@Pattern(regexp = Constants.PATTERN_LETTERS, message = Constants.ONLY_LETTERS_MESSAGE)
		@Size(min = 4, max = 50, message = Constants.DEFAULT_SIZE_MESSAGE)
    String firstName,

		@NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
		@Pattern(regexp = Constants.PATTERN_LETTERS, message = Constants.ONLY_LETTERS_MESSAGE)
		@Size(min = 4, max = 50, message = Constants.DEFAULT_SIZE_MESSAGE)
    String lastName,

		@NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
		@Pattern(regexp = Constants.PATTERN_NUMBERS, message = Constants.ONLY_NUMBERS_MESSAGE)
		@Size(min = 8, max = 11, message = Constants.DNI_SIZE_MESSAGE)
    String dni,

		@NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
		@Pattern(regexp = Constants.PATTERN_NUMBERS, message = Constants.ONLY_NUMBERS_MESSAGE)
		@Size(min = 10, max = 10, message = Constants.CELLPHONE_SIZE_MESSAGE)
    String cellphone,

		@NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
		@Email(message = Constants.EMAIL_INVALID_MESSAGE)
    String email,

		@NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
		@Pattern(regexp = Constants.PATTERN_PASSWORD, message = Constants.PASSWORD_INVALID_MESSAGE)
		@Size(min=4, max = 20, message = Constants.PASSWORD_SIZE_MESSAGE)
    String password
) { }
