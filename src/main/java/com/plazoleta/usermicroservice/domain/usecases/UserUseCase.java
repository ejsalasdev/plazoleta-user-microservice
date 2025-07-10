package com.plazoleta.usermicroservice.domain.usecases;

import java.util.List;
import java.util.Optional;

import com.plazoleta.usermicroservice.domain.exceptions.AuthenticationContextException;
import com.plazoleta.usermicroservice.domain.exceptions.ElementAlreadyExistsException;
import com.plazoleta.usermicroservice.domain.exceptions.ElementNotFoundException;
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

        RoleModel assignedRole = determineRoleFromSecurityContext();
        userModel.setRole(assignedRole);

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

    private RoleModel determineRoleFromSecurityContext() {
        try {
            List<String> currentRoles = authenticatedUserPort.getCurrentUserRoles()
                    .stream()
                    .map(String::toUpperCase)
                    .toList();

            if (currentRoles.contains(DomainConstants.ROLE_ADMIN)) {
                return DomainConstants.OWNER_ROLE;
            } else if (currentRoles.contains(DomainConstants.ROLE_OWNER)) {
                return DomainConstants.EMPLOYEE_ROLE;
            } else {
                return DomainConstants.CUSTOMER_ROLE;
            }
        } catch (Exception e) {
            return DomainConstants.CUSTOMER_ROLE;
        }
    }

    private void handleRestaurantAssociation(UserModel userModel) {
        String roleToCreate = userModel.getRole().getRoleEnum().toString().toUpperCase();

        if (roleToCreate.equals(DomainConstants.ROLE_EMPLOYEE)) {
            try {
                Long ownerId = authenticatedUserPort.getCurrentUserId();
                Optional<Long> restaurantId = restaurantServicePort.getRestaurantIdByOwnerId(ownerId);

                if (restaurantId.isEmpty()) {
                    throw new ElementNotFoundException(DomainExceptionsConstants.OWNER_HAS_NO_RESTAURANT);
                }

                userModel.setRestaurantId(restaurantId.get());
            } catch (ElementNotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new AuthenticationContextException(DomainExceptionsConstants.AUTHENTICATION_CONTEXT_UNAVAILABLE,
                        e);
            }
        }
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

}
