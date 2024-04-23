package com.pragma.user.domain.api;

import com.pragma.user.domain.models.User;

public interface IUserServicePort {

  void register(User user, boolean isWithoutEndpointPermissionUsing);

}
