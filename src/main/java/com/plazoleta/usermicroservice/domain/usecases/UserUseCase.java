package com.plazoleta.usermicroservice.domain.usecases;

import java.util.List;
import java.util.Optional;

import com.plazoleta.usermicroservice.domain.exceptions.ElementAlreadyExistsException;
import com.plazoleta.usermicroservice.domain.exceptions.ElementNotFoundException;
import com.plazoleta.usermicroservice.domain.exceptions.RoleNotAllowedException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.ports.in.UserServicePort;
import com.plazoleta.usermicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.usermicroservice.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.usermicroservice.domain.ports.out.RestaurantServicePort;
import com.plazoleta.usermicroservice.domain.ports.out.UserPersistencePort;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.UserValidatorChain;

public class UserUseCase implements UserServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final UserValidatorChain userValidatorChain;
    private final AuthenticatedUserPort authenticatedUserPort;
    private final RestaurantServicePort restaurantServicePort;

    public UserUseCase(UserPersistencePort userPersistencePort, PasswordEncoderPort passwordEncoderPort,
            UserValidatorChain userValidatorChain, AuthenticatedUserPort authenticatedUserPort,
            RestaurantServicePort restaurantServicePort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.userValidatorChain = userValidatorChain;
        this.authenticatedUserPort = authenticatedUserPort;
        this.restaurantServicePort = restaurantServicePort;
    }

    @Override
    public void save(UserModel userModel) {
        userValidatorChain.validate(userModel);

        // Asignar rol automáticamente según el contexto de autenticación
        RoleModel assignedRole = determineRoleFromSecurityContext();
        userModel.setRole(assignedRole);

        // Validar autorización y manejar asociación de restaurante
        validateRoleAuthorization(userModel);
        handleRestaurantAssociation(userModel);

        validateUserUniqueness(userModel);
        String encodedPassword = passwordEncoderPort.encode(userModel.getPassword());
        userModel.setPassword(encodedPassword);
        userPersistencePort.save(userModel);
    }

    @Override
    public UserModel getUserById(Long id) {
        Optional<UserModel> user = userPersistencePort.getUserById(id);

        if (user.isEmpty()) {
            throw new ElementNotFoundException(
                    String.format(DomainExceptionsConstants.USER_NOT_FOUND_BY_ID_MESSAGE, id));
        }
        return user.get();
    }

    private void validateRoleAuthorization(UserModel userModel) {
        try {
            List<String> currentRoles = authenticatedUserPort.getCurrentUserRoles()
                    .stream()
                    .map(String::toUpperCase)
                    .toList();
            String roleToCreate = userModel.getRole().getRoleEnum().toString().toUpperCase();
            
            // Validar reglas de negocio: ADMIN→OWNER, OWNER→EMPLOYEE
            if (roleToCreate.equals(DomainConstants.ROLE_EMPLOYEE) && !currentRoles.contains(DomainConstants.ROLE_OWNER)) {
                throw new RoleNotAllowedException(DomainExceptionsConstants.ONLY_OWNER_CAN_CREATE_EMPLOYEE);
            }
            if (roleToCreate.equals(DomainConstants.ROLE_OWNER) && !currentRoles.contains(DomainConstants.ROLE_ADMIN)) {
                throw new RoleNotAllowedException(DomainExceptionsConstants.ONLY_ADMIN_CAN_CREATE_OWNER);
            }
        } catch (RoleNotAllowedException e) {
            // Re-lanzar excepciones de autorización
            throw e;
        } catch (Exception e) {
            // Si no hay contexto de autenticación, debe ser CUSTOMER (ya validado en determineRoleFromSecurityContext)
            // No necesita validación adicional
        }
    }

    private void handleRestaurantAssociation(UserModel userModel) {
        String roleToCreate = userModel.getRole().getRoleEnum().toString().toUpperCase();

        // Solo los empleados necesitan asociación automática a restaurante
        if (roleToCreate.equals(DomainConstants.ROLE_EMPLOYEE)) {
            try {
                Long ownerId = authenticatedUserPort.getCurrentUserId();
                Optional<Long> restaurantId = restaurantServicePort.getRestaurantIdByOwnerId(ownerId);

                if (restaurantId.isEmpty()) {
                    throw new ElementNotFoundException(DomainExceptionsConstants.OWNER_HAS_NO_RESTAURANT);
                }

                userModel.setRestaurantId(restaurantId.get());
            } catch (ElementNotFoundException e) {
                // Re-lanzar excepción específica
                throw e;
            } catch (Exception e) {
                throw new RoleNotAllowedException("Cannot create employee without authenticated owner context");
            }
        }
        // Otros roles (OWNER, CUSTOMER, ADMIN) no necesitan restaurantId - se queda null automáticamente
    }

    private void validateUserUniqueness(UserModel userModel) {
        Optional<UserModel> userByDocument = userPersistencePort.getUserByDocumentId(userModel.getDocumentId());
        if (userByDocument.isPresent()) {
            throw new ElementAlreadyExistsException(
                    String.format(DomainExceptionsConstants.USER_DOCUMENT_ID_ALREADY_EXIST_MESSAGE,
                            userModel.getDocumentId()));
        }
        Optional<UserModel> userByEmail = userPersistencePort.getUserByEmail(userModel.getEmail());
        if (userByEmail.isPresent()) {
            throw new ElementAlreadyExistsException(
                    String.format(DomainExceptionsConstants.USER_EMAIL_ALREADY_EXIST_MESSAGE, userModel.getEmail()));
        }
        Optional<UserModel> userByPhone = userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber());
        if (userByPhone.isPresent()) {
            throw new ElementAlreadyExistsException(
                    String.format(DomainExceptionsConstants.USER_PHONE_NUMBER_ALREADY_EXIST_MESSAGE,
                            userModel.getPhoneNumber()));
        }
    }

    private RoleModel determineRoleFromSecurityContext() {
        try {
            // Obtener roles del usuario autenticado
            List<String> currentRoles = authenticatedUserPort.getCurrentUserRoles()
                    .stream()
                    .map(String::toUpperCase)
                    .toList();

            // Asignar rol según las reglas de negocio
            if (currentRoles.contains(DomainConstants.ROLE_ADMIN)) {
                // ADMIN puede crear OWNER
                return DomainConstants.OWNER_ROLE;
            } else if (currentRoles.contains(DomainConstants.ROLE_OWNER)) {
                // OWNER puede crear EMPLOYEE
                return DomainConstants.EMPLOYEE_ROLE;
            } else {
                // Cualquier otro caso o usuario no autenticado = CUSTOMER
                return DomainConstants.CUSTOMER_ROLE;
            }
        } catch (Exception e) {
            // Si no hay contexto de autenticación (registro público) = CUSTOMER
            return DomainConstants.CUSTOMER_ROLE;
        }
    }

}
