package com.plazoleta.usermicroservice.application.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.plazoleta.usermicroservice.application.dto.response.UserInfoResponse;
import com.plazoleta.usermicroservice.domain.model.UserModel;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserResponseMapper {

    default UserInfoResponse modelToResponse(UserModel userModel) {
        return new UserInfoResponse(
                userModel.getId(),
                userModel.getRole().getRoleEnum().name());
    }
}
