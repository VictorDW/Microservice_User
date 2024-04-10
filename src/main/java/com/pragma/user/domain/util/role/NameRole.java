package com.pragma.user.domain.util.role;

import java.util.Collections;
import java.util.List;

public enum NameRole {

  ADMINISTRATOR(List.of(
      Permission.REGISTER_ONE_TUTOR,
      Permission.REGISTER_ONE_STUDENT)
  ),
  TUTOR(List.of(
      Permission.REGISTER_ONE_STUDENT)
  ),
  STUDENT;

  private final List<Permission> permissions;

  NameRole(List<Permission> permissions) {
    this.permissions = permissions;
  }

  NameRole() {
    this.permissions = Collections.emptyList();
  }

  public List<Permission> getAllPermissions() {
    return permissions;
  }
}
