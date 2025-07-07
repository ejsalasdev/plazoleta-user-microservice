package com.plazoleta.usermicroservice.domain.usecases;

import com.plazoleta.usermicroservice.domain.exceptions.ElementAlreadyExistsException;
import com.plazoleta.usermicroservice.domain.exceptions.ElementNotFoundException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.ports.in.UserServicePort;
import com.plazoleta.usermicroservice.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.usermicroservice.domain.ports.out.UserPersistencePort;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;
import com.plazoleta.usermicroservice.domain.utils.validation.UserValidatorChain;

import java.util.Optional;

public class UserUseCase implements UserServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;
    private final UserValidatorChain userValidatorChain;

    public UserUseCase(UserPersistencePort userPersistencePort, PasswordEncoderPort passwordEncoderPort,
            UserValidatorChain userValidatorChain) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
        this.userValidatorChain = userValidatorChain;
    }

    @Override
    public void save(UserModel userModel) {
        userValidatorChain.validate(userModel);

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

        String encodedPassword = passwordEncoderPort.encode(userModel.getPassword());
        userModel.setPassword(encodedPassword);
        userModel.setRole(DomainConstants.OWNER_ROLE);

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
}
