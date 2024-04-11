package com.pragma.user.adapters.driving.controller;

import com.pragma.user.adapters.driving.adapter.IUserHandler;
import com.pragma.user.adapters.driving.dto.request.UserAdminRequest;
import com.pragma.user.adapters.driving.mapper.request.IUserAdminRequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  private final IUserHandler userHandler;
  private final IUserAdminRequestMapper userAdminRequestMapper;

  @PostMapping("/register/admin")
  public ResponseEntity<String> register(@RequestBody UserAdminRequest userAdminRequest) {
    userHandler.register(userAdminRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully");
  }

}
