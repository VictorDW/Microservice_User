package com.pragma.user.adapters.driven.auth.adapter;

import com.pragma.user.adapters.driven.jpa.mysql.entity.CustomerUserDetails;
import com.pragma.user.adapters.driven.jpa.mysql.mapper.IUserMapper;
import com.pragma.user.domain.models.Auth;
import com.pragma.user.domain.models.User;
import com.pragma.user.domain.spi.IAuthenticationPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@RequiredArgsConstructor
public class AuthAdapter implements IAuthenticationPort {

	private final AuthenticationManager authenticationManager;
	private final IUserMapper userMapper;

	@Override
	public User authentication(Auth auth) {

		Authentication userAuthenticate = new UsernamePasswordAuthenticationToken(auth.email(), auth.password());
		Authentication authenticatedUser = authenticationManager.authenticate(userAuthenticate);
		CustomerUserDetails customerUserDetails = (CustomerUserDetails) authenticatedUser.getPrincipal();

		return userMapper.entityToModel(customerUserDetails.userEntity());
	}
}
