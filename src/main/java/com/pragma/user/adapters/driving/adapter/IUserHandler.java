package com.pragma.user.adapters.driving.adapter;

import com.pragma.user.adapters.driving.dto.request.Request;

public interface IUserHandler {

  <T extends Request> void register(T t);
}
