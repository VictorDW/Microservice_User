package com.pragma.user.configuration.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pragma.user.configuration.error.ExceptionManager;
import com.pragma.user.configuration.error.dto.ExceptionResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@RequiredArgsConstructor
public class ExceptionConfig {

  private final Logger loggerClass = LoggerFactory.getLogger(ExceptionConfig.class);

  @Bean
  public AuthenticationEntryPoint jwtAuthenticationEntryPoint() {
    return (request, response, authException) -> {

      Object messageToken = request.getSession().getAttribute("error_token_message");

      if (messageToken != null) {

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(this.convertExceptionToString(messageToken.toString()));
      }
    };
  }

  private String convertExceptionToString(String message) {

    ExceptionResponse responseBody = ExceptionManager.generalExceptionHandler(message, HttpStatus.UNAUTHORIZED).getBody();
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.writeValueAsString(responseBody);
    } catch (JsonProcessingException e) {
      loggerClass.error(e.getMessage());
      return "";
    }
  }
}
