package com.plazoleta.usermicroservice.application.mappers;

import com.plazoleta.usermicroservice.application.dto.request.SaveUserRequest;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import com.plazoleta.usermicroservice.domain.model.RoleModel;
import com.plazoleta.usermicroservice.domain.enums.RoleName;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRequestMapper {

    @org.mapstruct.Mapping(target = "role", expression = "java(toRoleModel(saveUserRequest.role()))")
    UserModel requestToModel(SaveUserRequest saveUserRequest);

    default RoleModel toRoleModel(String role) {
        if (role == null) return null;
        return new RoleModel(null, RoleName.valueOf(role.toUpperCase()), null);
    }
}
