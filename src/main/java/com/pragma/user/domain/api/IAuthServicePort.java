package com.pragma.user.domain.api;

import com.pragma.user.domain.models.Auth;
import com.pragma.user.domain.models.JwtToken;

public interface IAuthServicePort {

	JwtToken login(Auth auth);
}
