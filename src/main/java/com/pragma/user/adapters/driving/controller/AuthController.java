package com.pragma.user.adapters.driving.controller;


import com.pragma.user.adapters.driving.adapter.IAuthHandler;
import com.pragma.user.adapters.driving.dto.request.AuthRequest;
import com.pragma.user.adapters.driving.dto.response.AuthResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final IAuthHandler authHandler;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
		return ResponseEntity.ok(authHandler.login(authRequest));
	}
}
