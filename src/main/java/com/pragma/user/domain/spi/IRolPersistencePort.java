package com.pragma.user.domain.spi;

import com.pragma.user.domain.models.Role;

import java.util.Optional;

public interface IRolPersistencePort {

	Optional<Role> getRoleById(Long id);
}
