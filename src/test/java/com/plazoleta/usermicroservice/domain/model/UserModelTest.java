package com.plazoleta.usermicroservice.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.plazoleta.usermicroservice.domain.enums.RoleName;
import org.junit.jupiter.api.Test;

class UserModelTest {
    @Test
    void testUserModelGettersAndSetters() {
        // Arrange
        RoleModel role = new RoleModel(1L, RoleName.OWNER, "Owner role");
        UserModel user = new UserModel(10L, "John", "Doe", "12345678", "+573001234567", "1990-01-01", "john@mail.com", "password", role);

        // Assert constructor and getters
        assertEquals(10L, user.getId());
        assertEquals("John", user.getName());
        assertEquals("Doe", user.getLastName());
        assertEquals("12345678", user.getDocumentId());
        assertEquals("+573001234567", user.getPhoneNumber());
        assertEquals("1990-01-01", user.getBirthDate());
        assertEquals("john@mail.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(role, user.getRole());

        // Setters
        user.setName("Jane");
        user.setLastName("Smith");
        user.setDocumentId("87654321");
        user.setPhoneNumber("+573009876543");
        user.setBirthDate("1995-05-05");
        user.setEmail("jane@mail.com");
        user.setPassword("newpass");
        RoleModel newRole = new RoleModel(2L, RoleName.ADMIN, "Admin role");
        user.setRole(newRole);

        // Assert setters
        assertEquals(10L, user.getId());
        assertEquals("Jane", user.getName());
        assertEquals("Smith", user.getLastName());
        assertEquals("87654321", user.getDocumentId());
        assertEquals("+573009876543", user.getPhoneNumber());
        assertEquals("1995-05-05", user.getBirthDate());
        assertEquals("jane@mail.com", user.getEmail());
        assertEquals("newpass", user.getPassword());
        assertEquals(newRole, user.getRole());
    }
}
