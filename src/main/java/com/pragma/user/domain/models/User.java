package com.pragma.user.domain.models;

import lombok.ToString;

@ToString
public class User {

  private final Long id;
  private final String firstName;
  private final String lastName;
  private final String dni;
  private final String cellphone;
  private final String email;
  private Role role;
  private final String password;

  public User(Long id, String firstName, String lastName, String dni, String cellphone, String email, String password) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.dni = dni;
    this.cellphone = cellphone;
    this.email = email;
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public Role getRole() {
    return role;
  }

  public String getEmail() {
    return email;
  }

  public String getCellphone() {
    return cellphone;
  }

  public String getDni() {
    return dni;
  }

  public String getLastName() {
    return lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public Long getId() {
    return id;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
