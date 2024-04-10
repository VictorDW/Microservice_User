package com.pragma.user.adapters.driving.mapper.request;

import com.pragma.user.adapters.driving.dto.request.UserAdminRequest;
import com.pragma.user.adapters.driving.dto.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IUserAdminRequestMapper {

  @Mapping(target = "idRol", ignore = true)
  UserRequest requestAdminToRequest(UserAdminRequest request);
}
