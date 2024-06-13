package com.pragma.user.adapters.driven.jpa.mysql.adapter;

import com.pragma.user.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.user.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.user.adapters.driven.jpa.mysql.mapper.IUserMapper;
import com.pragma.user.adapters.driven.jpa.mysql.repository.IUserRepository;
import com.pragma.user.domain.models.User;
import com.pragma.user.domain.util.role.TypeRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

  @InjectMocks
  private UserPersistenceAdapter userPersistenceAdapter;

  @Mock
  private IUserRepository userRepository;

  @Mock
  private IUserMapper userMapper;

  @Mock
  private PasswordEncoder passwordEncoder;


  @Test
  @DisplayName("Should execute the method at least once when you want to register a user")
  void test1() {

    // GIVEN
    User user = User.newUser()
        .email("test@example.com")
        .password("password")
        .build();

    UserEntity userEntity = UserEntity.builder()
        .email("test@example.com")
        .password("password")
        .build();

    given(userMapper.modelToEntity(user)).willReturn(userEntity);
    given(passwordEncoder.encode(userEntity.getPassword())).willReturn(userEntity.getPassword());

    // WHEN
    userPersistenceAdapter.saveUser(user);

    // THEN
    verify(userRepository, times(1)).save(userEntity);
  }

  @Test
  @DisplayName("Given a mail must return an optional with the user found in BD")
  void test2() {

    //GIVEN
    String email = "test@example.com";
    User user = User.newUser()
        .email("test@example.com")
        .build();

    UserEntity userEntity = UserEntity.builder()
        .email("test@example.com")
        .build();

    given(userRepository.findByEmail(email)).willReturn(Optional.of(userEntity));
    given(userMapper.entityToModel(userEntity)).willReturn(user);

    //WHEN
    Optional<User> result = userPersistenceAdapter.verifyUserByEmail(email);

    assertEquals(user, result.get());
  }

  @Test
  @DisplayName("Given a mail must return an empty optional")
  void test3() {

    //GIVEN
    String email = "test@example.com";

    given(userRepository.findByEmail(email)).willReturn(Optional.empty());

    // WHEN
    var result = userPersistenceAdapter.verifyUserByEmail(email);

    //THAT
    assertEquals(Optional.empty(), result);
  }

  @Test
  @DisplayName("Verify if the method returns true when a user with the admin role is present")
  void test4() {

    //GIVEN

    String adminRole = TypeRole.ADMIN.name();

    RoleEntity role = RoleEntity.builder()
        .rol(adminRole)
        .build() ;

    UserEntity adminUser = UserEntity.builder().role(role).build();
    List<UserEntity> users = Collections.singletonList(adminUser);

    given(userRepository.findByRole_Rol(adminRole)).willReturn(users);

    // WHEN

    boolean result = userPersistenceAdapter.isUserWithAdminRolePresent(adminRole);

    //THAT
    assertTrue(result);
  }

  @Test
  @DisplayName("Verify if the method returns false when a user with the admin role is present")
  void test5() {

    //GIVEN

    String adminRole = TypeRole.ADMIN.name();

    List<UserEntity> users = Collections.emptyList();

    given(userRepository.findByRole_Rol(adminRole)).willReturn(users);

    // WHEN

    boolean result = userPersistenceAdapter.isUserWithAdminRolePresent(adminRole);

    //THAT
    assertFalse(result);
  }
}