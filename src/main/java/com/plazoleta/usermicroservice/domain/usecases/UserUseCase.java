package com.plazoleta.usermicroservice.domain.usecases;

import com.plazoleta.usermicroservice.domain.exceptions.ElementAlreadyExistsException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.ports.in.UserServicePort;
import com.plazoleta.usermicroservice.domain.ports.out.PasswordEncoderPort;
import com.plazoleta.usermicroservice.domain.ports.out.UserPersistencePort;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainConstants;
import com.plazoleta.usermicroservice.domain.utils.constants.DomainExceptionsConstants;

import java.util.Optional;

public class UserUseCase implements UserServicePort {

    private final UserPersistencePort userPersistencePort;
    private final PasswordEncoderPort passwordEncoderPort;

    public UserUseCase(UserPersistencePort userPersistencePort, PasswordEncoderPort passwordEncoderPort) {
        this.userPersistencePort = userPersistencePort;
        this.passwordEncoderPort = passwordEncoderPort;
    }

    @Override
    public void save(UserModel userModel) {

        Optional<UserModel> user = userPersistencePort.getUserByDocumentId(userModel.getDocumentId());
        if (user.isPresent()) {
            throw new ElementAlreadyExistsException(
                    String.format(DomainExceptionsConstants.USER_ALREADY_EXIST_MESSAGE, userModel.getDocumentId())
            );
        }

        String encodedPassword = passwordEncoderPort.encode(userModel.getPassword());
        userModel.setPassword(encodedPassword);
        userModel.setRole(DomainConstants.OWNER_ROLE);

        userPersistencePort.save(userModel);
    }
}
