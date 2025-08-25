package com.conozca.prototype.utils;

import com.conozca.prototype.model.User;

// Esta clase la completaremos cuando tengas tus entidades definidas
public class TestDataBuilder {

    // Métodos para crear datos de prueba
    // Los agregaremos cuando definas tus entidades User, etc.

    public static final String TEST_EMAIL = "test@conozca.com";
    public static final String TEST_PASSWORD = "password123";
    public static final String INVALID_EMAIL = "invalid-email";

    // TODO: Agregar builders cuando tengas las entidades

    public static User defaultUser() {
        User user = new User();
        user.setPassword("defaultPassword");
        // Agrega aquí más campos si tu entidad User tiene más atributos
        return user;
    }

    public static User userWithPassword(String password) {
        User user = new User();
        user.setPassword(password);
        return user;
    }
}