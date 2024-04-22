package com.pragma.user.adapters.driving.controller;

import com.pragma.user.adapters.driving.adapter.IUserHandler;
import com.pragma.user.adapters.driving.dto.request.UserAdminRequest;
import com.pragma.user.adapters.driving.dto.request.UserRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final IUserHandler userHandler;

  @PreAuthorize("permitAll()")
  @PostMapping("/register/admin")
  public ResponseEntity<String> registerAdmin(@RequestBody @Valid UserAdminRequest request) {
    userHandler.register(request);
    return ResponseEntity.status(HttpStatus.CREATED).body("User admin created successfully");
  }

  @PreAuthorize("hasAnyRole('ADMIN', 'TUTOR')")
  @PostMapping("/register")
  public ResponseEntity<String> register(@RequestBody @Valid UserRequest request) {
    userHandler.register(request);
    return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
  }

}
