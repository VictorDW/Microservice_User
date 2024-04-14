package com.pragma.user.domain.spi;

import com.pragma.user.domain.models.User;

public interface IJwtServicePort {

	String generateToken(User subject);
}
