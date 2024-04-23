package com.pragma.user.domain.api.usecase;

import com.pragma.user.domain.api.IUserServicePort;
import com.pragma.user.domain.exception.AlreadyExistException;
import com.pragma.user.domain.exception.InitialEndpointUnavailableException;
import com.pragma.user.domain.exception.NotFoundException;
import com.pragma.user.domain.exception.WithoutPermitsException;
import com.pragma.user.domain.models.Role;
import com.pragma.user.domain.models.User;
import com.pragma.user.domain.spi.IRolPersistencePort;
import com.pragma.user.domain.spi.IUserPersistencePort;
import com.pragma.user.domain.util.DomainConstants;
import com.pragma.user.domain.util.role.TypeRole;

import java.util.Optional;
import java.util.function.Supplier;

public class UserUseCase implements IUserServicePort {

  private final IUserPersistencePort userPersistencePort;
  private final IRolPersistencePort rolPersistencePort;
  private final Supplier<String> roleSupplier;

  public UserUseCase(IRolPersistencePort rolPersistencePort,
                     IUserPersistencePort userPersistencePort,
                     Supplier<String> roleSupplier) {

    this.rolPersistencePort = rolPersistencePort;
    this.userPersistencePort = userPersistencePort;
    this.roleSupplier = roleSupplier;
  }

  @Override
  public void register(User user, boolean isWithoutEndpointPermissionUsing) {
    executeVerifyExistUser(user.getEmail());
    roleAssignment(user, isWithoutEndpointPermissionUsing);
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

  private void roleAssignment(User user, boolean isWithoutEndpointPermissionUsing) {

    Role role = isWithoutEndpointPermissionUsing ?
        getAdminRole() : findRoleById(user.getRole().getId());

    if (!isWithoutEndpointPermissionUsing) {
      executePermissionsValidation(roleSupplier.get(), role);
    }
    user.setRole(role);
  }

  private static void executePermissionsValidation(String authenticatedUserRole, Role role) {

    if (isTutorAssigningNonStudentRole(authenticatedUserRole, role)) {
      throw new WithoutPermitsException(
          String.format(
              DomainConstants.WITHOUT_PERMISSIONS_MESSAGE,
              role.getRol()));
    }
  }

  private static boolean isTutorAssigningNonStudentRole(String authenticatedUserRole, Role role) {
    return authenticatedUserRole.equals(TypeRole.TUTOR.name()) && !role.getRol().equals(TypeRole.STUDENT.name());
  }

  private Role getAdminRole() {
    verifyAdminRoleExistence();
    return rolPersistencePort.getRoleByName(Role.DEFAULT_ADMIN_ROL).orElseThrow(getNotFoundException());
  }

  private void verifyAdminRoleExistence() {
    if (userPersistencePort.isUserWithAdminRolePresent(Role.DEFAULT_ADMIN_ROL)) {
      throw new InitialEndpointUnavailableException(DomainConstants.INITIAL_ENDPOINT_UNAVAILABLE);
    }
  }

  private Role findRoleById(Long idRol) {
    return rolPersistencePort.getRoleById(idRol).orElseThrow(getNotFoundException());
  }
  
  private static Supplier<NotFoundException> getNotFoundException() {
    return () ->
        new NotFoundException(
            String.format(
                DomainConstants.NOT_FOUND_MESSAGE,
                DomainConstants.Class.ROLE.name()));
  }


}
