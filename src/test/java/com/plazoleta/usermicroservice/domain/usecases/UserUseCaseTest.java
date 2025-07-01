package com.plazoleta.usermicroservice.domain.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.plazoleta.usermicroservice.domain.exceptions.ElementAlreadyExistsException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
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
                null
        );
    }

    @Test
    void when_saveUserWithUniqueFields_then_userIsSaved() {
        // Arrange
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
        assertEquals(DomainConstants.OWNER_ROLE, userModel.getRole());
    }

    @Test
    void when_saveUserWithExistingDocumentId_then_throwException() {
        // Arrange
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.of(userModel));

        // Act & Assert
        assertThrows(ElementAlreadyExistsException.class, () -> userUseCase.save(userModel));
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, never()).save(any());
    }

    @Test
    void when_saveUserWithExistingEmail_then_throwException() {
        // Arrange
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
        when(userPersistencePort.getUserByDocumentId(userModel.getDocumentId())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByEmail(userModel.getEmail())).thenReturn(Optional.empty());
        when(userPersistencePort.getUserByPhoneNumber(userModel.getPhoneNumber())).thenReturn(Optional.of(userModel));

        // Act & Assert
        assertThrows(ElementAlreadyExistsException.class, () -> userUseCase.save(userModel));
        verify(userValidatorChain, times(1)).validate(userModel);
        verify(userPersistencePort, never()).save(any());
    }
}
