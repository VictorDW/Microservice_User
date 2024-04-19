package com.pragma.user.adapters.driving.mapper.request;

import com.pragma.user.adapters.driving.dto.request.UserRequest;
import com.pragma.user.domain.models.Role;
import com.pragma.user.domain.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Objects;

@Mapper(componentModel = "spring")
public interface IUserRequestMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "role", source = "idRol")
  User requestToModel(UserRequest request);

  default Role toRole(Long id) {

    if (Objects.isNull(id)) {
      return null;
    }

    return Role.newRole()
              .id(id)
              .build();
  }
}
