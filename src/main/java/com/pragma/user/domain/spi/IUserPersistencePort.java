package com.pragma.user.domain.spi;

import com.pragma.user.domain.models.User;

public interface IUserPersistencePort {

  void saveUser(User user);
}
