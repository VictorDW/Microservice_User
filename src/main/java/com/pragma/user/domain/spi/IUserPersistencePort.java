package com.pragma.user.domain.spi;

import com.pragma.user.domain.models.User;

import java.util.Optional;

public interface IUserPersistencePort {

  void saveUser(User user);

  Optional<User> verifyUserByEmail(String email);
}
