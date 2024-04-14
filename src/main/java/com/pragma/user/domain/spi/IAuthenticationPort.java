package com.pragma.user.domain.spi;

import com.pragma.user.domain.models.Auth;
import com.pragma.user.domain.models.User;

public interface IAuthenticationPort {

	User authentication(Auth auth);

}
