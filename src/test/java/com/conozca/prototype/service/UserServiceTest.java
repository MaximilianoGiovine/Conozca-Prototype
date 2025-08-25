package com.conozca.prototype.service;

import com.conozca.prototype.exception.MissingFieldException;
import com.conozca.prototype.exception.UserNotFoundException;
import com.conozca.prototype.model.User;
import com.conozca.prototype.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardarUsuario_ok() {
        User user = new User();
        user.setEmail("abc@abc.com");
        user.setPassword("1234abc.dfg");

        when(passwordEncoder.encode("1234abc.dfg")).thenReturn("hashed");

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User saved = userService.guardarUsuario(user);

        assertEquals("hashed", saved.getPassword());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void guardarUsuario_nullUser() {
        assertThrows(MissingFieldException.class, () -> userService.guardarUsuario(null));
    }

    @Test
    void guardarUsuario_emptyPassword() {
        User user = new User();
        user.setPassword("");
        assertThrows(MissingFieldException.class, () -> userService.guardarUsuario(user));
    }

    @Test
    void listarUsuarios_ok() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(new User()));
        List<User> users = userService.listarUsuarios();
        assertEquals(1, users.size());
    }

    @Test
    void buscarUserPorId_found() {
        User user = new User();
        when(userRepository.findById(1)).thenReturn(Optional.of(user));
        assertNotNull(userService.buscarUserPorId(1));
    }

    @Test
    void buscarUserPorId_notFound() {
        // Simula que el usuario con ID 1 no existe
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        // Verifica que se lanza la excepción esperada
        assertThrows(UserNotFoundException.class, () -> {
            userService.buscarUserPorId(1);
        });
    }

    @Test
    void eliminarUserPorId_ok() {
        when(userRepository.existsById(1)).thenReturn(true);
        userService.eliminarUserPorId(1);
        verify(userRepository).deleteById(1);
    }

    @Test
    void eliminarUserPorId_notFound() {
        when(userRepository.existsById(1)).thenReturn(false);
        assertThrows(UserNotFoundException.class, () -> userService.eliminarUserPorId(1));
    }
}
