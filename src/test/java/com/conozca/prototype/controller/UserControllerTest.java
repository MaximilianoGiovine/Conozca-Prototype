package com.conozca.prototype.controller;


import com.conozca.prototype.TestConfig;
import com.conozca.prototype.exception.ApiExceptionHandlerUser;
import com.conozca.prototype.model.User;
import com.conozca.prototype.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import({ApiExceptionHandlerUser.class, TestConfig.class})
class UserControllerTest {

    @MockBean
    private IUserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAllUsers_ok() throws Exception {
        when(userService.listarUsuarios()).thenReturn(Collections.singletonList(new User()));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk());
    }

    @Test
    void getUserById_ok() throws Exception {
        User user = new User();
        user.setId(1);
        when(userService.buscarUserPorId(1)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void createUser_ok() throws Exception {
        User user = new User();
        user.setId(1); // ✅ Asegura que el ID esté presente
        user.setPassword("1234");

        when(userService.guardarUsuario(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"password\":\"1234\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    void deleteUser_ok() throws Exception {
        doNothing().when(userService).eliminarUserPorId(1);

        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent()); // <-- Cambiado a NO_CONTENT
    }
}
