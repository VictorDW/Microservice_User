package com.pragma.user.adapters.driving.dto.request;

public class UserAdminRequest extends Request {

  public UserAdminRequest(String firstName,
                          String lastName,
                          String dni,
                          String cellphone,
                          String email,
                          String password) {

    super(firstName, lastName, dni, cellphone, email, password);
    isWithoutEndpointPermissionUsing = true;
  }
}
