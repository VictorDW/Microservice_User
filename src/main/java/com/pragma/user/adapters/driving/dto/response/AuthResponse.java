package com.pragma.user.adapters.driving.dto.response;

public record AuthResponse(

		Long id,
		String firstName,
		String lastName,
		String role,
		String token
) {
}
