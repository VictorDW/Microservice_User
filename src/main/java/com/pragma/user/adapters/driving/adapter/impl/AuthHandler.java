package com.pragma.user.adapters.driving.adapter.impl;

import com.pragma.user.adapters.driving.adapter.IAuthHandler;
import com.pragma.user.adapters.driving.dto.request.AuthRequest;
import com.pragma.user.adapters.driving.dto.response.AuthResponse;
import com.pragma.user.adapters.driving.mapper.request.IAuthRequestMapper;
import com.pragma.user.adapters.driving.mapper.response.IAuthResponseMapper;
import com.pragma.user.domain.api.IAuthServicePort;
import com.pragma.user.domain.models.JwtToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {

	private final IAuthServicePort authServicePort;
	private final IAuthRequestMapper authRequestMapper;
	private final IAuthResponseMapper authResponseMapper;

	@Override
	public AuthResponse login(AuthRequest request) {
		var auth = authRequestMapper.requestToAuth(request);
		JwtToken jwtToken = authServicePort.login(auth);
		return authResponseMapper.authToResponse(jwtToken);
	}
}
