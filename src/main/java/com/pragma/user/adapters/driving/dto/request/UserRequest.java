package com.pragma.user.adapters.driving.dto.request;

import com.pragma.user.configuration.Constants;
import jakarta.validation.constraints.*;


public class UserRequest extends Request{

  @NotNull(message = Constants.FIELD_NOT_NULL_MESSAGE)
  private final Long idRole;

  public UserRequest(String firstName,
                     String lastName,
                     String dni,
                     String cellphone,
                     String email,
                     Long idRole,
                     String password) {

    super(firstName, lastName, dni, cellphone, email, password);
    this.idRole = idRole;
    super.setIdRol(this.idRole);
    isWithoutEndpointPermissionUsing = false;
  }
}
