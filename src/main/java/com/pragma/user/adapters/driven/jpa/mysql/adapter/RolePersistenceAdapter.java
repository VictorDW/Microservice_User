package com.pragma.user.adapters.driven.jpa.mysql.adapter;

import com.pragma.user.adapters.driven.jpa.mysql.mapper.IRoleMapper;
import com.pragma.user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.user.domain.models.Role;
import com.pragma.user.domain.spi.IRolPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RolePersistenceAdapter implements IRolPersistencePort {

	private final IRoleRepository roleRepository;
	private final IRoleMapper roleMapper;

	@Override
	public Optional<Role> getRoleById(Long id) {
		return roleRepository.findById(id)
				.map(roleMapper::entityToModel);
	}

	@Override
	public Optional<Role> getRoleByName(String typeRole) {
		return roleRepository.findByRol(typeRole)
				.map(roleMapper::entityToModel);
	}
}
