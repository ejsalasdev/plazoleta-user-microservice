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
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.exceptions.ElementAlreadyExistsException;
import com.plazoleta.usermicroservice.domain.exceptions.ElementNotFoundException;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.ports.out.AuthenticatedUserPort;
import com.plazoleta.usermicroservice.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.usermicroservice.domain.ports.out.RestaurantServicePort;
import com.plazoleta.usermicroservice.domain.ports.out.UserPersistencePort;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
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
    @Mock
    private RestaurantServicePort restaurantServicePort;

    @Captor
    private ArgumentCaptor<UserModel> userModelCaptor;

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
                new RoleModel(1L, RoleName.OWNER, "Owner role"),
                null);
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
    void when_adminCreatesUser_then_assignOwnerRoleAutomatically() {
        // Arrange
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_ADMIN));
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.empty());

        // Act
        userUseCase.save(userModel);

        // Assert
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, times(1)).save(userModelCaptor.capture());
        
        UserModel savedUser = userModelCaptor.getValue();
        assertEquals(DomainConstants.OWNER_ROLE, savedUser.getRole());
    }

    @Test
    void when_ownerCreatesUser_then_assignEmployeeRoleAutomatically() {
        // Arrange
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_OWNER));
        when(authenticatedUserPort.getCurrentUserId()).thenReturn(1L);
        when(restaurantServicePort.getRestaurantIdByOwnerId(1L)).thenReturn(Optional.of(100L));
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.empty());

        // Act
        userUseCase.save(userModel);

        // Assert
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, times(1)).save(userModelCaptor.capture());
        
        UserModel savedUser = userModelCaptor.getValue();
        assertEquals(DomainConstants.EMPLOYEE_ROLE, savedUser.getRole());
        assertEquals(100L, savedUser.getRestaurantId());
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
    void when_ownerTriesToCreateEmployeeWithoutRestaurant_then_throwElementNotFoundException() {
        // Arrange
        // OWNER should be able to create EMPLOYEE, but they need a restaurant
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_OWNER));
        when(authenticatedUserPort.getCurrentUserId()).thenReturn(1L);
        when(restaurantServicePort.getRestaurantIdByOwnerId(1L)).thenReturn(Optional.empty()); // No restaurant
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.empty());

        // Act & Assert
        ElementNotFoundException ex = assertThrows(ElementNotFoundException.class, () -> userUseCase.save(userModel));
        assertEquals(DomainExceptionsConstants.OWNER_HAS_NO_RESTAURANT, ex.getMessage());
    }

    @Test
    void when_anyoneCreatesUser_then_assignCustomerRoleAutomatically() {
        // Arrange
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_CUSTOMER));
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.empty());

        // Act
        userUseCase.save(userModel);

        // Assert
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, times(1)).save(any());
        assertEquals(DomainConstants.CUSTOMER_ROLE, userModel.getRole());
    }

    @Test
    void when_savePublicClientRegistrationWithNullRoles_then_assignCustomerRole() {
        // Arrange
        UserModel clientModel = new UserModel(
                null,
                "Maria",
                "Rodriguez",
                "87654321",
                "+573009876543",
                null,
                "maria.rodriguez@gmail.com",
                "rawPassword",
                null, // No role specified
                null  // No restaurant for client
        );
        String encodedPassword = "encodedPassword123";

        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(null); // No authenticated user
        when(passwordEncoderPort.encode("rawPassword")).thenReturn(encodedPassword);
        when(userPersistencePort.getUserByDocumentId("87654321")).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail("maria.rodriguez@gmail.com")).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber("+573009876543")).thenReturn(Optional.empty());

        // Act
        userUseCase.save(clientModel);

        // Assert
        verify(userValidatorChain, times(1)).validate(clientModel);
        verify(passwordEncoderPort, times(1)).encode("rawPassword");
        verify(userPersistencePort, times(1)).save(clientModel);
        assertEquals(DomainConstants.CUSTOMER_ROLE, clientModel.getRole());
        assertEquals(encodedPassword, clientModel.getPassword());
    }

    @Test
    void when_savePublicClientRegistrationWithEmptyRoles_then_assignCustomerRole() {
        // Arrange
        UserModel clientModel = new UserModel(
                null,
                "Carlos",
                "Lopez",
                "12345678",
                "+573001234567",
                null,
                "carlos.lopez@gmail.com",
                "password123",
                null,
                null);

        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of()); // Empty roles list
        when(passwordEncoderPort.encode("password123")).thenReturn("encodedPassword456");
        when(userPersistencePort.getUserByDocumentId("12345678")).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail("carlos.lopez@gmail.com")).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber("+573001234567")).thenReturn(Optional.empty());

        // Act
        userUseCase.save(clientModel);

        // Assert
        assertEquals(DomainConstants.CUSTOMER_ROLE, clientModel.getRole());
        verify(userPersistencePort, times(1)).save(clientModel);
    }

    @Test
    void when_savePublicClientRegistrationWithException_then_assignCustomerRole() {
        // Arrange
        UserModel clientModel = new UserModel(
                null,
                "Ana",
                "Gutierrez",
                "98765432",
                "+573008765432",
                null,
                "ana.gutierrez@gmail.com",
                "secure123",
                null,
                null);

        when(authenticatedUserPort.getCurrentUserRoles()).thenThrow(new RuntimeException("No authentication")); // Exception
                                                                                                                // indicates
                                                                                                                // no
                                                                                                                // auth
        when(passwordEncoderPort.encode("secure123")).thenReturn("encodedPassword789");
        when(userPersistencePort.getUserByDocumentId("98765432")).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail("ana.gutierrez@gmail.com")).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber("+573008765432")).thenReturn(Optional.empty());

        // Act
        userUseCase.save(clientModel);

        // Assert
        assertEquals(DomainConstants.CUSTOMER_ROLE, clientModel.getRole());
        verify(userPersistencePort, times(1)).save(clientModel);
    }

    @Test
    void when_adminCreatesOwner_then_saveSuccessfully() {
        // Arrange
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_ADMIN));
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(userModel.getPassword())).thenReturn("encodedPassword");

        // Act
        userUseCase.save(userModel);

        // Assert
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, times(1)).save(any());
    }

    @Test
    void when_ownerCreatesEmployeeWithRestaurant_then_saveSuccessfully() {
        // Arrange
        when(authenticatedUserPort.getCurrentUserRoles()).thenReturn(List.of(DomainConstants.ROLE_OWNER));
        when(authenticatedUserPort.getCurrentUserId()).thenReturn(1L);
        when(restaurantServicePort.getRestaurantIdByOwnerId(1L)).thenReturn(Optional.of(100L));
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.empty());
        when(passwordEncoderPort.encode(userModel.getPassword())).thenReturn("encodedPassword");

        // Act
        userUseCase.save(userModel);

        // Assert
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, times(1)).save(any());
    }
}
