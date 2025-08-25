// TestConstants.java - Colocar en: test/java/com/conozca/prototype/utils/TestConstants.java  
package com.conozca.prototype.utils;

public class TestConstants {

    // URLs de la API
    public static final String AUTH_BASE_URL = "/api/auth";
    public static final String REGISTER_URL = AUTH_BASE_URL + "/register";
    public static final String LOGIN_URL = AUTH_BASE_URL + "/login";

    // Datos de prueba
    public static final String TEST_EMAIL = "test@conozca.com";
    public static final String TEST_PASSWORD = "password123";
    public static final String TEST_NAME = "Test User";
    public static final String INVALID_EMAIL = "invalid-email";
    public static final String WEAK_PASSWORD = "123";
    public static final String DEFAULT_PASSWORD = "defaultPassword";
    public static final String ENCRYPTED_PASSWORD = "encryptedPassword";

    // Mensajes esperados
    public static final String USER_REGISTERED_SUCCESS = "User registered successfully";
    public static final String LOGIN_SUCCESS = "Login successful";
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String USER_ALREADY_EXISTS = "User already exists";
}