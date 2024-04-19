package com.pragma.user.configuration.jwt;

import com.pragma.user.adapters.driven.jpa.mysql.entity.CustomerUserDetails;
import com.pragma.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.user.configuration.Constants;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final Logger loggerClass = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
  private final IJwtServiceAuthorization jwtService;


  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
                                  @NonNull HttpServletResponse response,
                                  @NonNull FilterChain filterChain) throws ServletException, IOException {

    final var token = getTokenFromRequest(request);

    if(token.isEmpty()) {
      request.getSession().setAttribute("error_token_message", Constants.TOKEN_NOT_FOUND_MESSAGE);
      filterChain.doFilter(request, response);
      return;
    }

    try {
      Authentication authenticatedUser = extractAuthenticatedUserFromToken(token.get());
      updateSecurityContext(authenticatedUser);

    } catch (JwtException e) {
      loggerClass.error("INVALID TOKEN: %s".formatted(e.getMessage()));
      request.getSession().setAttribute("error_token_message", e.getMessage());

    } finally {
      filterChain.doFilter(request, response);
    }

  }

  private Optional<String> getTokenFromRequest(HttpServletRequest request) {

    final String authHeader = request.getHeader("Authorization");

    if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {
      return Optional.of(authHeader.substring(7));
    }
    return Optional.empty();
  }

  private Authentication extractAuthenticatedUserFromToken(String token) {

    String emailFromUser = jwtService.getSubjectFromToken(token);
    String roleFromUser = jwtService.getRoleFromToken(token);

    UserDetails userDetails = generateUserDetailsForSecurityContext(emailFromUser, roleFromUser);

    return new UsernamePasswordAuthenticationToken(
        userDetails,
        null,
        userDetails.getAuthorities());
  }

  private void updateSecurityContext(Authentication authenticatedUser) {
    SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
  }

  private UserDetails generateUserDetailsForSecurityContext(String email, String role) {

    UserEntity userEntity = UserEntity.builder()
        .email(email)
        .role(RoleEntity.builder()
            .rol(role)
            .build())
        .build();
    return new CustomerUserDetails(userEntity);
  }


}