package com.pragma.user.configuration.security;

import com.pragma.user.adapters.driven.jpa.mysql.entity.CustomerUserDetails;
import com.pragma.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.user.configuration.Constants;
import com.pragma.user.configuration.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.function.Supplier;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final AccessDeniedHandler permissionsAccessDeniedHandler;
	private final IUserRepository userRepository;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		return http
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(sessionConfig ->
						sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
				.exceptionHandling(exceptionConfig -> exceptionConfig
						.authenticationEntryPoint(jwtAuthenticationEntryPoint)
						.accessDeniedHandler(permissionsAccessDeniedHandler))
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

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
	public Supplier<String> getAuthenticatedUser() {
		return () -> {
			var customerUserDetails = (CustomerUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			UserEntity user = customerUserDetails.userEntity();
			return user.getRole().getRol();
		};
	}
}
