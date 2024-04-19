package com.pragma.user.domain.api.usecase;

import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.exception.AlreadyExistException;
import com.pragma.user.domain.exception.NotFoundException;
import com.pragma.user.domain.models.Role;
import com.pragma.user.domain.models.User;
import com.pragma.user.domain.spi.IRolPersistencePort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.domain.util.DomainConstants;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public class UserUseCase implements IUserServicePort {

  private final IUserPersistencePort userPersistencePort;
  private final IRolPersistencePort rolPersistencePort;
  private final PasswordEncoder passwordEncoder;

  public UserUseCase(IRolPersistencePort rolPersistencePort,
                     IUserPersistencePort userPersistencePort,
                     PasswordEncoder passwordEncoder) {

    this.rolPersistencePort = rolPersistencePort;
    this.userPersistencePort = userPersistencePort;
	  this.passwordEncoder = passwordEncoder;
  }

  @Override
  public void register(User user) {

    executeVerifyExistUser(user.getEmail());
    executeRoleAssignment(user);
    encoderPassword(user);
    userPersistencePort.saveUser(user);
  }

  private void executeVerifyExistUser(String email) {

    Optional<User> verifiedUser = userPersistencePort.verifyUserByEmail(email);

    verifiedUser.ifPresent(
        existUser -> {
          throw new AlreadyExistException(
              String.format(
                  DomainConstants.ALREADY_EXIST_MESSAGE,
                  existUser.getEmail()));
        });
  }

  /*  private void executeRoleAssignment(User user) {

//    Role role = Objects.isNull(user.getRole()) ?
//        getAdminRole() : verifyRol(user.getRole().getId());

    Optional<Role> role = Optional.ofNullable(user.getRole())
        .map(Role::getId)
        .flatMap(this::verifyRol);

    user.setRole(role.orElseGet(this::getAdminRole));
  }
 */

  /* private Optional<Role> verifyRol(Long idRol) {
   return rolPersistencePort.getRoleById(idRol);
  }
  */

  private void executeRoleAssignment(User user) {

    var role = Objects.isNull(user.getRole()) ?
        getAdminRole() : findRoleById(user.getRole().getId());

    user.setRole(role.orElseThrow(getNotFoundException()));
  }

  private Optional<Role> getAdminRole() {
    return rolPersistencePort.getRoleByName(Role.DEFAULT_ADMIN_ROL);
  }

  private Optional<Role> findRoleById(Long idRol) {
    return rolPersistencePort.getRoleById(idRol);
  }
  
  private static Supplier<NotFoundException> getNotFoundException() {
    return () ->
        new NotFoundException(
            String.format(
                DomainConstants.NOT_FOUND_MESSAGE,
                DomainConstants.Class.ROLE.name()));
  }
  
  
  private void encoderPassword(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }

}
