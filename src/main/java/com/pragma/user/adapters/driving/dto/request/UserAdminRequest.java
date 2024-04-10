package com.pragma.user.adapters.driving.dto.request;

public record UserAdminRequest(
    String firstName,
    String lastName,
    String dni,
    String cellphone,
    String email,
    String password
) { }
