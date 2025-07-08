package com.plazoleta.usermicroservice.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.exceptions.ElementAlreadyExistsException;
import com.plazoleta.usermicroservice.domain.exceptions.ElementNotFoundException;
import com.plazoleta.usermicroservice.domain.exceptions.RoleNotAllowedException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.usermicroservice.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.usermicroservice.domain.ports.out.UserPersistencePort;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.UserValidatorChain;

class UserUseCaseTest {

    @Mock
    private UserPersistencePort userPersistencePort;
    @Mock
    private PasswordEncoderPort passwordEncoderPort;
    @Mock
    private UserValidatorChain userValidatorChain;
    @Mock
    private AuthenticatedUserPort authenticatedUserPort;

    @InjectMocks
    private UserUseCase userUseCase;

    private UserModel userModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userModel = new UserModel(
                null,
                "Test",
                "User",
                "12345678",
                "+573001234567",
                "1990-01-01",
                "test@mail.com",
                "plainpass",
                new RoleModel(1L, RoleName.OWNER, "Owner role"));
        // Default: user has ADMIN role for positive tests
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_ADMIN));
    }

    @Test
    void when_saveUserWithUniqueFields_then_userIsSaved() {
        // Arrange
        // (roles already set in setUp)
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(userModel.getPassword())).thenReturn("encodedpass");

        // Act
        userUseCase.save(userModel);

        // Assert
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, times(1)).save(userModel);
        assertEquals("encodedpass", userModel.getPassword());
        assertEquals("OWNER", userModel.getRole().getRoleEnum().name());
    }

    @Test
    void when_saveUserWithExistingDocumentId_then_throwException() {
        // Arrange
        // (roles already set in setUp)
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.of(userModel));

        // Act & Assert
        assertThrows(ElementAlreadyExistsException.class, () -> userUseCase.save(userModel));
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void when_saveUserWithExistingEmail_then_throwException() {
        // Arrange
        // (roles already set in setUp)
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.of(userModel));

        // Act & Assert
        assertThrows(ElementAlreadyExistsException.class, () -> userUseCase.save(userModel));
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void when_saveUserWithExistingPhone_then_throwException() {
        // Arrange
        // (roles already set in setUp)
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.of(userModel));

        // Act & Assert
        assertThrows(ElementAlreadyExistsException.class, () -> userUseCase.save(userModel));
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void when_saveUserWithRoleOwnerAndNoAdminRole_then_throwRoleNotAllowedException() {
        // Arrange
        userModel.setRole(new RoleModel(2L, RoleName.OWNER, "Owner role"));
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_EMPLOYEE)); // Not
                                                                                                              // ADMIN
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoleNotAllowedException.class, () -> userUseCase.save(userModel));
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void when_saveUserWithRoleEmployeeAndNoOwnerRole_then_throwRoleNotAllowedException() {
        // Arrange
        userModel.setRole(new RoleModel(3L, RoleName.EMPLOYEE, "Employee role"));
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_ADMIN)); // Not OWNER
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(RoleNotAllowedException.class, () -> userUseCase.save(userModel));
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void when_getUserByIdWithNonExistentId_then_throwElementNotFoundException() {
        // Arrange
        Long userId = 999L;
        when(userPersistencePort.getUserById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ElementNotFoundException.class, () -> userUseCase.getUserById(userId));
    }

    @Test
    void when_getUserByIdWithExistingId_then_returnUser() {
        // Arrange
        Long userId = 1L;
        when(userPersistencePort.getUserById(userId)).thenReturn(Optional.of(userModel));

        // Act
        UserModel result = userUseCase.getUserById(userId);

        // Assert
        assertEquals(userModel, result);
    }

    @Test
    void when_createEmployeeWithoutOwnerRole_then_throwRoleNotAllowedException() {
        // Arrange
        userModel.setRole(new com.plazoleta.usermicroservice.domain.model.RoleModel(2L, com.plazoleta.usermicroservice.domain.enums.RoleName.EMPLOYEE, "Employee role"));
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_ADMIN)); // No OWNER

        // Act & Assert
        RoleNotAllowedException ex = assertThrows(RoleNotAllowedException.class, () -> userUseCase.save(userModel));
        assertEquals(com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants.ONLY_OWNER_CAN_CREATE_EMPLOYEE, ex.getMessage());
    }

    @Test
    void when_createOwnerWithoutAdminRole_then_throwRoleNotAllowedException() {
        // Arrange
        userModel.setRole(new com.plazoleta.usermicroservice.domain.model.RoleModel(3L, com.plazoleta.usermicroservice.domain.enums.RoleName.OWNER, "Owner role"));
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_EMPLOYEE)); // No ADMIN

        // Act & Assert
        RoleNotAllowedException ex = assertThrows(RoleNotAllowedException.class, () -> userUseCase.save(userModel));
        assertEquals(com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants.ONLY_ADMIN_CAN_CREATE_OWNER, ex.getMessage());
    }
}
