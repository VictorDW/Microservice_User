package com.pragma.user.adapters.driving.adapter.impl;

import com.pragma.user.adapters.driving.adapter.IUserHandler;
import com.pragma.user.adapters.driving.dto.request.Request;
import com.pragma.user.adapters.driving.mapper.request.IUserRequestMapper;
import com.pragma.user.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

  private  final IUserServicePort userServicePort;
  private final IUserRequestMapper userRequestMapper;

  @Override
  public <T extends Request> void register(T t) {
    var user = userRequestMapper.requestToModel(t);
    userServicePort.register(user);
  }
}
