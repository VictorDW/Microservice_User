package com.pragma.user.domain.models;

public class User {

  private final Long id;
  private final String firstName;
  private final String lastName;
  private final String dni;
  private final String cellphone;
  private final String email;
  private Role role;
  private final String password;

  public User(BuilderUser builder) {
    this.id = builder.id;
    this.firstName = builder.firstName;
    this.lastName = builder.lastName;
    this.dni = builder.dni;
    this.cellphone = builder.cellphone;
    this.email = builder.email;
    this.role = builder.role;
    this.password = builder.password;
  }

  public static BuilderUser builder() {
    return null;
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

  public static BuilderUser newUser() {
    return new BuilderUser();
  }

  public static class BuilderUser {

    private Long id;
    private String firstName;
    private String lastName;
    private String dni;
    private String cellphone;
    private String email;
    private Role role;
    private String password;

    private BuilderUser() {
      //default empty constructor
    }

    public BuilderUser id(Long id) {
      this.id = id;
      return this;
    }

    public BuilderUser firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public BuilderUser lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public BuilderUser dni(String dni) {
      this.dni = dni;
      return this;
    }

    public BuilderUser cellphone(String cellphone) {
      this.cellphone = cellphone;
      return this;
    }

    public BuilderUser email(String email) {
      this.email = email;
      return this;
    }

    public BuilderUser role(Role role) {
      this.role = role;
      return this;
    }

    public BuilderUser password(String password) {
      this.password = password;
      return this;
    }

    public User build() {
      return new User(this);
    }
  }
}
