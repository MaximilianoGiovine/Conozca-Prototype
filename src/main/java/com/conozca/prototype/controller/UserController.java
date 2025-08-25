package com.conozca.prototype.controller;

import com.conozca.prototype.DTO.LoginRequest;
import com.conozca.prototype.DTO.LoginResponse;
import com.conozca.prototype.model.User;
import com.conozca.prototype.service.IUserService;

import com.conozca.prototype.service.UserService;
import lombok.RequiredArgsConstructor;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final IUserService userService;


    @GetMapping
    public List<User> getAllUsers() {
        return userService.listarUsuarios();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Integer id) {
        return userService.buscarUserPorId(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.guardarUsuario(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") Integer id) {
        userService.eliminarUserPorId(id);
        return ResponseEntity.noContent().build(); // Devuelve 204 No Content
    }

    @RestController
    @RequestMapping("/api/auth")
    public class AuthController {

        private final UserService userService;

        @Autowired
        public AuthController(UserService userService) {
            this.userService = userService;
        }

        @PostMapping("/login")
        public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
            User user = userService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponse(user));
        }
    }
}