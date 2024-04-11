package com.pragma.user.domain.api;

import com.pragma.user.domain.exception.AlreadyExistException;
import com.pragma.user.domain.exception.NotFoundException;
import com.pragma.user.domain.models.Role;
import com.pragma.user.domain.models.User;
import com.pragma.user.domain.spi.IRolPersistencePort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.domain.util.role.TypeRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

	@InjectMocks
	private UserUseCase userUseCase;

	@Mock
	private IUserPersistencePort userPersistencePort;

	@Mock
	private IRolPersistencePort rolPersistencePort;

	private User user;

	@BeforeEach
	void setUp() {
		user = new User(null, "test", "test", "test", "test", "test", "test");
	}

	@Test
	@DisplayName("Should throw an exception if the user is already registered with the given email address")
	void test1() {

		//GIVEN
		given(userPersistencePort.verifyUserByEmail("test")).willReturn(Optional.of(user));

		//WHEN - THEN
		assertThrows(AlreadyExistException.class, () -> userUseCase.register(user));
	}

	@Test
	@DisplayName("Given a null in the role, you must assign it the administrator role")
	void test2() {

		Long idRol = Role.DEFAULT_ADMIN_ROL;
		Role role = Role.newRole()
								.id(idRol)
								.rol("ADMIN")
								.build();

	//GIVEN
		given(rolPersistencePort.getRoleById(idRol)).willReturn(Optional.of(role));

	//WHEN
		userUseCase.register(user);

	//THEN
		assertAll(
				() -> assertNotNull(user.getRole()),
				() -> assertEquals(idRol, user.getRole().getId()),
				() -> assertEquals(TypeRole.ADMIN.name(), user.getRole().getRol())
		);
	}

	@Test
	@DisplayName("Given a role must query the DB and assign it to the user")
	void test3() {

		//GIVEN
		Role role = Role.newRole()
								.id(2L)
								.rol("TUTOR")
								.build();
		user.setRole(role);
		given(rolPersistencePort.getRoleById(role.getId())).willReturn(Optional.of(role));

		//WHEN
		userUseCase.register(user);

		//THEN
		assertAll(
				() -> assertNotNull(user.getRole()),
				() -> assertEquals(2L, user.getRole().getId()),
				() -> assertEquals(TypeRole.TUTOR.name(), user.getRole().getRol())
		);
	}

	@Test
	@DisplayName("Should throw an exception when the role is not found in the DB")
	void test4() {

		//GIVEN
		given(rolPersistencePort.getRoleById(Role.DEFAULT_ADMIN_ROL)).willReturn(Optional.empty());

		//WHEN - THEN
		assertThrows(NotFoundException.class, () -> userUseCase.register(user));
	}

	@Test
	@DisplayName("Should execute the method at least once when you want to register a user")
	void test5() {

		//GIVEN
		Role role = Role.newRole()
				.id(2L)
				.rol("TUTOR")
				.build();

		user.setRole(role);
		given(rolPersistencePort.getRoleById(role.getId())).willReturn(Optional.of(role));

		//WHEN
		userUseCase.register(user);

		//THEN
		verify(userPersistencePort, times(1)).saveUser(user);
	}
}