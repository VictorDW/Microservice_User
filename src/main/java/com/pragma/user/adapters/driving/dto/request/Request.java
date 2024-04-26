package com.pragma.user.adapters.driving.dto.request;

import com.pragma.user.configuration.Constants;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class Request {

  protected boolean isWithoutEndpointPermissionUsing;

  @NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
  @Pattern(regexp = Constants.PATTERN_LETTERS, message = Constants.ONLY_LETTERS_MESSAGE)
  @Size(min = 4, max = 50, message = Constants.DEFAULT_SIZE_MESSAGE)
  protected String firstName;

  @NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
  @Pattern(regexp = Constants.PATTERN_LETTERS, message = Constants.ONLY_LETTERS_MESSAGE)
  @Size(min = 4, max = 50, message = Constants.DEFAULT_SIZE_MESSAGE)
  protected String lastName;

  @NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
  @Pattern(regexp = Constants.PATTERN_NUMBERS, message = Constants.ONLY_NUMBERS_MESSAGE)
  @Size(min = 8, max = 11, message = Constants.DNI_SIZE_MESSAGE)
  protected String dni;

  @NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
  @Pattern(regexp = Constants.PATTERN_NUMBERS, message = Constants.ONLY_NUMBERS_MESSAGE)
  @Size(min = 10, max = 10, message = Constants.CELLPHONE_SIZE_MESSAGE)
  protected String cellphone;

  @NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
  @Email(message = Constants.EMAIL_INVALID_MESSAGE)
  protected String email;

  @Setter
  protected Long idRol;

  @NotBlank(message = Constants.FIELD_EMPTY_MESSAGE)
  @Pattern(regexp = Constants.PATTERN_PASSWORD, message = Constants.PASSWORD_INVALID_MESSAGE)
  @Size(min=4, max = 20, message = Constants.PASSWORD_SIZE_MESSAGE)
  protected String password;

  protected Request(String firstName,
                    String lastName,
                    String dni,
                    String cellphone,
                    String email,
                    String password) {

    this.password = password;
    this.email = email;
    this.cellphone = cellphone;
    this.dni = dni;
    this.lastName = lastName;
    this.firstName = firstName;
  }

  @Hidden
  public boolean isWithoutEndpointPermissionUsing() {
    return isWithoutEndpointPermissionUsing;
  }
}
