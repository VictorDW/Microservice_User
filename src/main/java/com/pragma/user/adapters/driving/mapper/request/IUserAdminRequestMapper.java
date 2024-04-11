package com.pragma.user.adapters.driving.mapper.request;

import com.pragma.user.adapters.driving.dto.request.UserAdminRequest;
import com.pragma.user.domain.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserAdminRequestMapper {

  User requestToModel(UserAdminRequest request);
}
