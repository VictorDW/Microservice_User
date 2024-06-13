package com.pragma.user.adapters.driven.jpa.mysql.adapter;

import com.pragma.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.user.adapters.driven.jpa.mysql.mapper.IRoleMapper;
import com.pragma.user.adapters.driven.jpa.mysql.repository.IRoleRepository;
import com.pragma.user.domain.models.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.offset;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RolePersistenceAdapterTest {

  @InjectMocks
  private RolePersistenceAdapter rolePersistenceAdapter;

  @Mock
  private IRoleRepository roleRepository;

  @Mock
  private IRoleMapper roleMapper;

  private final String roleName = "ADMIN";

  @Test
  @DisplayName("When the role with the given id exists, the function should return the rol")
  void test1() {

    // GIVEN
    Long id = 2L;
    Role expectedRole = Role.newRole()
        .id(id)
        .build();
    RoleEntity roleEntity = RoleEntity.builder()
        .id(id)
        .build();

    given(roleRepository.findById(id)).willReturn(Optional.of(roleEntity));
    given(roleMapper.entityToModel(any())).willReturn(expectedRole);

    // When
    Optional<Role> actualRole = rolePersistenceAdapter.getRoleById(id);

    // Then
    assertThat(actualRole).isEqualTo(Optional.of(expectedRole));

  }

  @Test
  @DisplayName("When the role with the given id does not exist, the function should return an empty")
  void test2() {

    // GIVEN
    Long id = 2L;

    given(roleRepository.findById(id)).willReturn(Optional.empty());

    // When
    Optional<Role> actualRole = rolePersistenceAdapter.getRoleById(id);

    // Then
    assertThat(actualRole).isEmpty();

  }

  @Test
  @DisplayName("When a role with the given name exists, the method returns the role")
  void test3() {

    // Given
    RoleEntity roleEntity = RoleEntity.builder()
        .rol(roleName)
        .build();

    Role expectedRole = Role.newRole()
        .rol(roleName)
        .build();

    given(roleRepository.findByRol(roleName)).willReturn(Optional.of(roleEntity));
    given(roleMapper.entityToModel(roleEntity)).willReturn(expectedRole);

    // When
    Optional<Role> result = rolePersistenceAdapter.getRoleByName(roleName);

    // Then
    assertTrue(result.isPresent());
    assertEquals(expectedRole, result.get());
  }

  @Test
  @DisplayName("When a role with the given name does not exist, the method returns an empty optional")
  void test4() {

    // Given
    given(roleRepository.findByRol(roleName)).willReturn(Optional.empty());

    // When
    Optional<Role> result = rolePersistenceAdapter.getRoleByName(roleName);

    // Then
    assertFalse(result.isPresent());
  }

  @Test
  @DisplayName("When the role repository throws an exception, the method should throw a runtime exception")
  void testGetRoleByName_WhenRoleRepositoryThrowsException_ThrowsRuntimeException() {

    // Given
    given(roleRepository.findByRol(roleName)).willThrow(new RuntimeException("Database error"));

    // When
    Throwable exception = assertThrows(RuntimeException.class, () -> rolePersistenceAdapter.getRoleByName(roleName));

    // Then
    assertEquals("Database error", exception.getMessage());
  }
}