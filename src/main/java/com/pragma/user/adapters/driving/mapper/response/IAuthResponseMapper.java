package com.pragma.user.adapters.driving.mapper.response;

import com.pragma.user.adapters.driving.dto.response.AuthResponse;
import com.pragma.user.domain.models.JwtToken;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IAuthResponseMapper {

	@Mapping(target = "id", source = "user.id")
	@Mapping(target = "firstName", source = "user.firstName")
	@Mapping(target = "lastName", source = "user.lastName")
	@Mapping(target = "role", source = "user.role.rol")
	AuthResponse authToResponse(JwtToken jwtToken);
}
