package com.pragma.user.configuration;


import com.pragma.user.adapters.driven.auth.adapter.AuthAdapter;
import com.pragma.user.adapters.driven.jpa.mysql.adapter.RolePersistenceAdapter;
import com.pragma.user.adapters.driven.jpa.mysql.adapter.UserPersistenceAdapter;
import com.pragma.user.adapters.driven.jpa.mysql.entity.CustomerUserDetails;
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
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final IJwtServicePort jwtServicePort;

  private final IUserRepository userRepository;
  private final IRoleRepository roleRepository;
  private final IUserMapper userMapper;
  private final IRoleMapper roleMapper;

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {

    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService());
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

    return daoAuthenticationProvider;
  }

  @Bean
  public IAuthenticationPort authentication(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return new AuthAdapter(authenticationManager(authenticationConfiguration), userMapper);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return email ->
      userRepository
          .findByEmail(email)
          .map(CustomerUserDetails::new)
          .orElseThrow(() ->
              new UsernameNotFoundException(
                  String.format(
                  Constants.USER_NOT_FOUND_MESSAGE,
                  email))
          );
  }


  @Bean
  public IUserPersistencePort userPersistence() {
    return new UserPersistenceAdapter(userRepository, userMapper);
  }

  @Bean
  public IRolPersistencePort rolePersistence() {
    return new RolePersistenceAdapter(roleRepository, roleMapper);
  }

  @Bean
  public IUserServicePort userService() {
    return new UserUseCase(rolePersistence(), userPersistence(), passwordEncoder());
  }

  @Bean
  IAuthServicePort authService(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return new AuthUseCase(authentication(authenticationConfiguration), jwtServicePort);
  }


}
