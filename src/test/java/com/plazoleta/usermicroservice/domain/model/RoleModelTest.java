package com.plazoleta.usermicroservice.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import org.junit.jupiter.api.Test;

class RoleModelTest {
    @Test
    void testRoleModelGettersAndSetters() {
        // Arrange
        RoleModel role = new RoleModel(1L, RoleName.OWNER, "Owner role");

        // Assert constructor and getters
        assertEquals(1L, role.getId());
        assertEquals(RoleName.OWNER, role.getRoleEnum());
        assertEquals("Owner role", role.getDescription());

        // Setters
        role.setId(2L);
        role.setRoleEnum(RoleName.ADMIN);
        role.setDescription("Admin role");

        // Assert setters
        assertEquals(2L, role.getId());
        assertEquals(RoleName.ADMIN, role.getRoleEnum());
        assertEquals("Admin role", role.getDescription());
    }
}
