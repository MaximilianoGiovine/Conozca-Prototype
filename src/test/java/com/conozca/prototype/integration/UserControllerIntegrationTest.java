package com.conozca.prototype.integration;

import com.conozca.prototype.model.User;
import com.conozca.prototype.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void cleanDb() {
        userRepository.deleteAll();
    }

    @Test
    void createAndGetUser_flow() throws Exception {
        // Crear usuario con email y password
        String json = "{\"email\":\"test@correo.com\", \"password\":\"testpass\"}";

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                        .with(csrf()) // ✅ Token CSRF
                        .with(user("admin").roles("ADMIN"))) // ✅ Usuario simulado
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.password", notNullValue()))
                .andExpect(jsonPath("$.password", matchesPattern("^\\$2[aby]\\$.{56}$")))
                .andExpect(jsonPath("$.email", is("test@correo.com")));

        // Listar usuarios
        mockMvc.perform(get("/api/users")
                        .with(user("admin").roles("ADMIN"))) // ✅ Usuario simulado también para GET
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }




    @Test
    void getUserById_flow() throws Exception {
        User user = new User();
        user.setPassword("myPass");
        user = userRepository.save(user);

        mockMvc.perform(get("/api/users/" + user.getId())
                        .with(user("admin").roles("ADMIN"))) // ✅ Usuario simulado con rol
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(user.getId().intValue())))
                .andExpect(jsonPath("$.password", not(emptyOrNullString())));
    }

    @Test
    void deleteUser_flow() throws Exception {
        User user = new User();
        user.setEmail("toDelete@correo.com");
        user.setPassword("toDelete");
        user = userRepository.save(user);

        mockMvc.perform(delete("/api/users/" + user.getId())
                        .with(csrf()) // ✅ Token CSRF
                        .with(user("admin").roles("ADMIN"))) // ✅ Usuario simulado
                .andExpect(status().isNoContent());

        // Verifica que el usuario ya no existe
        mockMvc.perform(get("/api/users/" + user.getId())
                        .with(user("admin").roles("ADMIN")))
                .andExpect(status().isNotFound()) // ✅ 404 esperado
                .andExpect(jsonPath("$.error", containsString("no existe"))); // ✅ mensaje de error
    }

    @Test
    void deleteUser_notFound() throws Exception {
        mockMvc.perform(delete("/api/users/9999")
                        .with(csrf()) // ✅ Token CSRF
                        .with(user("admin").roles("ADMIN"))) // ✅ Usuario simulado
                .andExpect(status().isNotFound()); // ✅ 404 si el usuario no existe
    }
}
