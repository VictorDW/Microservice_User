package com.pragma.user.adapters.driven.jpa.mysql.repository;

import com.pragma.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IRoleRepository extends JpaRepository<RoleEntity, Long> {

  Optional<RoleEntity> findByRol(String name);
}
