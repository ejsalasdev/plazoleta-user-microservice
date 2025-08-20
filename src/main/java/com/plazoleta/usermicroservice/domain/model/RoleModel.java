package com.plazoleta.usermicroservice.domain.model;

import com.plazoleta.usermicroservice.domain.enums.RoleName;

public class RoleModel {

    private Long id;
    private RoleName name;
    private String description;

    public RoleModel(Long id, RoleName name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoleName getRoleEnum() {
        return name;
    }

    public void setRoleEnum(RoleName roleName) {
        this.name = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
