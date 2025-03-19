package com.electrik.electrik_ecomm.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.electrik.electrik_ecomm.enums.Roles;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    public void testNoArgsConstructor() {
        User user = new User();
        assertNotNull(user);
    }

    @Test
    public void testConstructorWithEmailAndPassword() {
        String email = "test@example.com";
        String password = "password123";
        User user = new User(email, password);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testConstructorWithAllFields() {
        UUID id = UUID.randomUUID();
        String name = "John";
        String lastName = "Doe";
        String email = "john.doe@example.com";
        String password = "password123";
        Roles role = Roles.USER;

        User user = new User(id, name, lastName, email, password, role);

        assertEquals(id, user.getId());
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
        assertEquals(role, user.getRole());
    }
}
