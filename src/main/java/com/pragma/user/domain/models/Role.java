package com.pragma.user.domain.models;

public class Role {

  private final Long id;
  private final String rol;
  private final String description;
  public static final long DEFAULT_ADMIN_ROL = 1L;

  public Role(Builder builder) {
    this.id = builder.id;
    this.rol = builder.rol;
    this.description = builder.description;
  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public String getRol() {
    return rol;
  }

  public static Builder newRole() {
    return new Builder();
  }

  public static final class Builder {

    private Long id;
    private String rol;
    private String description;

    public Builder() {
      //default empty constructor
    }

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder rol(String rol) {
      this.rol = rol;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Role build() {
      return new Role(this);
    }
  }
}
