package com.plazoleta.usermicroservice.domain.ports.out;

import com.plazoleta.usermicroservice.domain.model.UserModel;

import java.util.Optional;

public interface UserPersistencePort {
    void save(UserModel userModel);

    Optional<UserModel> getUserByDocumentId(String documentId);

    Optional<UserModel> getUserByEmail(String email);

    Optional<UserModel> getUserByPhoneNumber(String phoneNumber);

    Optional<UserModel> getUserById(Long id);
}
