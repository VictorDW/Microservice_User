package com.pragma.user.configuration;


import com.pragma.user.adapters.driven.jpa.mysql.adapter.RolePersistenceAdapter;
import com.pragma.user.adapters.driven.jpa.mysql.adapter.UserPersistenceAdapter;
import com.pragma.user.adapters.driven.jpa.mysql.mapper.IRoleMapper;
import com.pragma.user.adapters.driven.jpa.mysql.mapper.IUserMapper;
import com.pragma.user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.api.UserUseCase;
import com.pragma.user.domain.spi.IRolPersistencePort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final IUserRepository userRepository;
  private final IRoleRepository roleRepository;
  private final IUserMapper userMapper;
  private final IRoleMapper roleMapper;

  @Bean
  public IUserPersistencePort userPersistence() {
    return new UserPersistenceAdapter(userRepository, userMapper);
  }

  @Bean
  public IRolPersistencePort rolePersistence() {
    return new RolePersistenceAdapter(roleRepository, roleMapper);
  }

  @Bean
  public IUserServicePort userServicePort() {
    return new UserUseCase(rolePersistence(),userPersistence());
  }
}
