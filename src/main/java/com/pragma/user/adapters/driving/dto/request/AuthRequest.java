package com.pragma.user.adapters.driving.dto.request;

import com.pragma.user.configuration.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record AuthRequest(
		@NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
		@Email(message = Constants.EMAIL_INVALID_MESSAGE)
		String email,

		@NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
		@Pattern(regexp = Constants.PATTERN_PASSWORD, message = Constants.PASSWORD_INVALID_MESSAGE)
		@Size(min=4, max = 20, message = Constants.PASSWORD_SIZE_MESSAGE)
		String password
) {
}
