package com.pragma.user.domain.models;

import com.pragma.user.domain.util.role.NameRole;
import lombok.ToString;

@ToString
public class Role {

  private Long id;
  private NameRole rol;
  private String description;

  public Role() {
    //Solo para mapstruct
  }

  public Role(Long id, NameRole rol, String description) {
    this.id = id;
    this.rol = rol;
    this.description = description;
  }

  public Long getId() {
    return id;
  }

  public String getDescription() {
    return description;
  }

  public NameRole getRol() {
    return rol;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setRol(NameRole rol) {
    this.rol = rol;
  }
}
