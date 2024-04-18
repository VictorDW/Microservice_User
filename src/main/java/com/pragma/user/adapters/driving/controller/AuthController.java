package com.pragma.user.adapters.driving.controller;


import com.pragma.user.adapters.driving.adapter.IAuthHandler;
import com.pragma.user.adapters.driving.dto.request.AuthRequest;
import com.pragma.user.adapters.driving.dto.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final IAuthHandler authHandler;

	@PreAuthorize("permitAll()")
	@PostMapping("/login")
	public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthRequest authRequest) {
		return ResponseEntity.ok(authHandler.login(authRequest));
	}

}
