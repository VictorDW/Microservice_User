package com.pragma.user.domain.api;

import com.pragma.user.domain.exception.AlreadyExistException;
import com.pragma.user.domain.exception.NotFoundException;
import com.pragma.user.domain.models.Role;
import com.pragma.user.domain.models.User;
import com.pragma.user.domain.spi.IRolPersistencePort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.domain.util.DomainConstants;

import java.util.Objects;
import java.util.Optional;

public class UserUseCase implements IUserServicePort {

  private final IUserPersistencePort userPersistencePort;
  private final IRolPersistencePort rolPersistencePort;

  public UserUseCase(IRolPersistencePort rolPersistencePort, IUserPersistencePort userPersistencePort) {
    this.rolPersistencePort = rolPersistencePort;
    this.userPersistencePort = userPersistencePort;
  }

  @Override
  public void register(User user) {
    executeVerifyExistUser(user.getEmail());
    executeAdminCreation(user);
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

  private void executeAdminCreation(User user) {
    if (Objects.isNull(user.getRole())) {
      user.setRole(verifyRol(Role.DEFAULT_ADMIN_ROL));
    } else {
      verifyRol(user.getRole().getId());
    }
  }

  private Role verifyRol(Long idRol) {
   return rolPersistencePort.getRoleById(idRol)
       .orElseThrow(() ->
         new NotFoundException(
             String.format(
                 DomainConstants.NOT_FOUND_MESSAGE,
                 DomainConstants.Class.ROL.name()))
       );
  }

}
