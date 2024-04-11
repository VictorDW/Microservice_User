package com.pragma.user.adapters.driven.jpa.mysql.mapper;

import com.pragma.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.user.domain.models.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring") //builder = @Builder(disableBuilder = true)
public interface IRoleMapper {

	@Mapping(target = "id", source = "roleEntity.id")
	@Mapping(target = "rol", source = "roleEntity.rol")
	@Mapping(target = "description", source = "roleEntity.description")
	Role entityToModel(RoleEntity roleEntity);
}

/*
@Builder es una anotación interna de MapStruct que se utiliza para configurar el patrón de diseño de Builder.
El atributo disableBuilder se establece en true para indicar que MapStruct no debe intentar buscar métodos de builder en la clase de destino.
En su lugar, MapStruct utilizará el método de builder que proporcionas en tu interfaz de mapper.
*/
