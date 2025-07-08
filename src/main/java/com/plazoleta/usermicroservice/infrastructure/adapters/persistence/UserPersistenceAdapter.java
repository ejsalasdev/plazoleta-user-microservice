package com.plazoleta.usermicroservice.infrastructure.adapters.persistence;

import java.util.Optional;

import com.plazoleta.usermicroservice.domain.exceptions.ElementNotFoundException;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.ports.out.UserPersistencePort;
import com.plazoleta.usermicroservice.infrastructure.entities.UserEntity;
import com.plazoleta.usermicroservice.infrastructure.mappers.UserEntityMapper;
import com.plazoleta.usermicroservice.infrastructure.repositories.postgres.RoleRepository;
import com.plazoleta.usermicroservice.infrastructure.repositories.postgres.UserRepository;
import com.plazoleta.usermicroservice.infrastructure.utils.constants.InfrastructureMessagesConstants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final UserEntityMapper userEntityMapper;
    private final RoleRepository roleRepository;

    @Override
    public void save(UserModel userModel) {
        UserEntity userEntity = userEntityMapper.modelToEntity(userModel);
        var roleName = userModel.getRole().getRoleEnum();
        var roleEntity = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ElementNotFoundException(
                        String.format(InfrastructureMessagesConstants.ROLE_NOT_FOUND, roleName)));
        userEntity.setRole(roleEntity);
        userRepository.save(userEntity);
    }

    @Override
    public Optional<UserModel> getUserByDocumentId(String documentId) {
        return userRepository.findByDocumentId(documentId).map(userEntityMapper::entityToModel);
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return userRepository.findByEmail(email).map(userEntityMapper::entityToModel);
    }

    @Override
    public Optional<UserModel> getUserByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).map(userEntityMapper::entityToModel);
    }

    @Override
    public Optional<UserModel> getUserById(Long id) {
        return userRepository.findById(id).map(userEntityMapper::entityToModel);
    }
}
