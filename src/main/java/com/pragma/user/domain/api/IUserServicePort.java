package com.pragma.user.domain.api;

import com.pragma.user.domain.models.User;

public interface IUserServicePort {

  User register(User user);

}
