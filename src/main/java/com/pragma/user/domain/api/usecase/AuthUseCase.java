package com.pragma.user.domain.api.usecase;

import com.pragma.user.domain.api.IAuthServicePort;
import com.pragma.user.domain.models.Auth;
import com.pragma.user.domain.models.JwtToken;
import com.pragma.user.domain.models.User;
import com.pragma.user.domain.spi.IAuthenticationPort;
import com.pragma.user.domain.spi.IJwtServicePort;

public class AuthUseCase implements IAuthServicePort {

	private final IAuthenticationPort authenticationPort;
	private final IJwtServicePort jwtServicePort;

	public AuthUseCase(IAuthenticationPort authenticationPort, IJwtServicePort jwtServicePort) {
		this.authenticationPort = authenticationPort;
		this.jwtServicePort = jwtServicePort;
	}

	@Override
	public JwtToken login(Auth auth) {

		User authUser = authenticationPort.authentication(auth);
		String token = jwtServicePort.generateToken(authUser);

		return new JwtToken(authUser, token);
	}
}
