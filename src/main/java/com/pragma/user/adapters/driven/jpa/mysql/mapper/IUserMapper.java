package com.pragma.user.adapters.driven.jpa.mysql.mapper;

import com.pragma.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.user.domain.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {IRoleMapper.class})
public interface IUserMapper {

	UserEntity modelToEntity(User user);

	User entityToModel(UserEntity user);
}
