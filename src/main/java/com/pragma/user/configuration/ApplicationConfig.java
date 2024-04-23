package com.pragma.user.configuration;

import com.pragma.user.adapters.driven.auth.adapter.AuthAdapter;
import com.pragma.user.adapters.driven.jpa.mysql.adapter.RolePersistenceAdapter;
import com.pragma.user.adapters.driven.jpa.mysql.adapter.UserPersistenceAdapter;
import com.pragma.user.adapters.driven.jpa.mysql.mapper.IRoleMapper;
import com.pragma.user.adapters.driven.jpa.mysql.mapper.IUserMapper;
import com.pragma.user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.user.domain.api.usecase.AuthUseCase;
import com.pragma.user.domain.api.IAuthServicePort;
import com.pragma.user.domain.spi.IJwtServicePort;
import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.api.usecase.UserUseCase;
import com.pragma.user.domain.spi.IAuthenticationPort;
import com.pragma.user.domain.spi.IRolPersistencePort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final IJwtServicePort jwtServicePort;
  private final IUserRepository userRepository;
  private final IRoleRepository roleRepository;
  private final IUserMapper userMapper;
  private final IRoleMapper roleMapper;
  private final PasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;
  private final Supplier<String> roleSupplier;


  @Bean
  public IUserPersistencePort userPersistence() {
    return new UserPersistenceAdapter(userRepository, userMapper, passwordEncoder);
  }

  @Bean
  public IRolPersistencePort rolePersistence() {
    return new RolePersistenceAdapter(roleRepository, roleMapper);
  }

  @Bean
  public IUserServicePort userService() {
    return new UserUseCase(rolePersistence(), userPersistence(), roleSupplier);
  }

  @Bean
  public IAuthServicePort authService() {
    return new AuthUseCase(authentication(), jwtServicePort);
  }

  @Bean
  public IAuthenticationPort authentication() {
    return new AuthAdapter(authenticationManager, userMapper);
  }


}
