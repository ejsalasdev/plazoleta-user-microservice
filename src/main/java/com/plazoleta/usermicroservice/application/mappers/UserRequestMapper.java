package com.plazoleta.usermicroservice.application.mappers;

import com.plazoleta.usermicroservice.application.dto.request.SaveUserRequest;
import com.plazoleta.usermicroservice.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserRequestMapper {

    @Mapping(target = "role", ignore = true)
    @Mapping(target = "restaurantId", ignore = true)
    UserModel requestToModel(SaveUserRequest saveUserRequest);
}
