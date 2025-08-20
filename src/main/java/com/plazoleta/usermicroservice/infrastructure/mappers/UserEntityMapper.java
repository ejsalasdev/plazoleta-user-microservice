package com.plazoleta.usermicroservice.infrastructure.mappers;

import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.infrastructure.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoleEntityMapper.class})
public interface UserEntityMapper {
    
    @Mapping(source = "role", target = "role")
    @Mapping(source = "restaurantId", target = "restaurantId")
    UserEntity modelToEntity(UserModel userModel);
    
    @Mapping(source = "role", target = "role")
    @Mapping(source = "restaurantId", target = "restaurantId")
    UserModel entityToModel(UserEntity userEntity);
}
