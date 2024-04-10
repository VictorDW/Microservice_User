package com.pragma.user.configuration;


import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.api.UserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public IUserServicePort userServicePort() {
    return new UserUseCase();
  }
}
