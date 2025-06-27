package com.plazoleta.usermicroservice.infrastructure.mappers;

import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.infrastructure.entities.RolEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleEntityMapper {

    RolEntity modelToEntity(RoleModel roleModel);
    
    RoleModel entityToModel(RolEntity rolEntity);
}
