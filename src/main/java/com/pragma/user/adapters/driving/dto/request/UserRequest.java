package com.pragma.user.adapters.driving.dto.request;

public record UserRequest(
    String firstName,
    String lastName,
    String dni,
    String cellphone,
    String email,
    Long idRol,
    String password
) {
}
