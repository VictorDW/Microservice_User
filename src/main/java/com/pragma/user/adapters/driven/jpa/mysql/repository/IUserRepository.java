package com.pragma.user.adapters.driven.jpa.mysql.repository;

import com.pragma.user.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {

	Optional<UserEntity> findByEmail(String email);
	List<UserEntity> findByRole_Rol(String role);
}
