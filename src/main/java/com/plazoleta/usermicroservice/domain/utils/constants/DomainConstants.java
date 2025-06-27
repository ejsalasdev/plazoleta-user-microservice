package com.plazoleta.usermicroservice.domain.utils.constants;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import com.plazoleta.usermicroservice.domain.model.RoleModel;

public class DomainConstants {

    public static final RoleModel OWNER_ROLE = new RoleModel(2L, RoleName.OWNER, "User with seller role");

    private DomainConstants() {

        throw new IllegalStateException("Utility Class");
    }
}
