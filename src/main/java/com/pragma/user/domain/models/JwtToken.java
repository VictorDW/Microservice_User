package com.pragma.user.domain.models;

public record JwtToken(

		User user,
		String token

) { }
