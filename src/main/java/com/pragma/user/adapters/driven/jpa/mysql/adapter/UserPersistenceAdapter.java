package com.pragma.user.adapters.driven.jpa.mysql.adapter;

import com.pragma.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.user.adapters.driven.jpa.mysql.mapper.IUserMapper;
import com.pragma.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.user.domain.models.User;
import com.pragma.user.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserPersistenceAdapter implements IUserPersistencePort {

	private final IUserRepository userRepository;
	private final IUserMapper userMapper;

	@Override
	public void saveUser(User user) {
		UserEntity userEntity = userMapper.modelToEntity(user);
		userRepository.save(userEntity);
	}

	@Override
	public Optional<User> verifyUserByEmail(String email) {
		return userRepository.findByEmail(email).map(userMapper::entityToModel);
	}
}
