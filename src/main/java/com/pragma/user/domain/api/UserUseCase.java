package com.pragma.user.domain.api;

import com.pragma.user.domain.models.Role;
import com.pragma.user.domain.models.User;
import com.pragma.user.domain.util.role.NameRole;

import java.util.Objects;

public class UserUseCase implements IUserServicePort {

  @Override
  public User register(User user) {
    executeValidateAdminCreation(user);
    return user;
  }

  private void executeValidateAdminCreation(User user) {

    if (Objects.isNull(user.getRole())) {
      Role role = new Role();
      role.setId(1L);
      role.setRol(NameRole.ADMINISTRATOR);
      user.setRole(role);
    }
  }
}
