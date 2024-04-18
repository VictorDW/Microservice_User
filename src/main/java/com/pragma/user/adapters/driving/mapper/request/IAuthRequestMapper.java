package com.pragma.user.adapters.driving.mapper.request;

import com.pragma.user.adapters.driving.dto.request.AuthRequest;
import com.pragma.user.domain.models.Auth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAuthRequestMapper {

	Auth requestToAuth(AuthRequest authRequest);
}
