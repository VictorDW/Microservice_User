package com.pragma.user.adapters.driving.adapter.impl;

import com.pragma.user.adapters.driving.adapter.IUserHandler;
import com.pragma.user.adapters.driving.dto.request.UserAdminRequest;
import com.pragma.user.adapters.driving.dto.request.UserRequest;
import com.pragma.user.adapters.driving.mapper.request.IUserAdminRequestMapper;
import com.pragma.user.adapters.driving.mapper.request.IUserRequestMapper;
import com.pragma.user.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

  private  final IUserServicePort userServicePort;
  private final IUserRequestMapper userRequestMapper;
  private final IUserAdminRequestMapper userAdminRequestMapper;


  @Override
  public void register(UserAdminRequest request) {
    var user = userAdminRequestMapper.requestToModel(request);
    userServicePort.register(user);
  }
}
