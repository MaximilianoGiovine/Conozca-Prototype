package com.conozca.prototype.controller;

import com.conozca.prototype.DTO.LoginRequest;
import com.conozca.prototype.DTO.LoginResponse;
import com.conozca.prototype.config.JwtUtil;
import com.conozca.prototype.model.User;
import com.conozca.prototype.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private final IUserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Autenticar usuario
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(), 
                    loginRequest.getPassword()
                )
            );

            // Cargar detalles del usuario
            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getUsername());
            
            // Generar token JWT
            String token = jwtUtil.generateToken(userDetails);

            // Crear respuesta
            LoginResponse response = new LoginResponse(token, userDetails.getUsername());
            
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales inválidas");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error en el servidor: " + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        try {
            User savedUser = userService.guardarUsuario(user);
            
            // Crear UserDetails para generar token
            UserDetails userDetails = userDetailsService.loadUserByUsername(savedUser.getUsername());
            String token = jwtUtil.generateToken(userDetails);
            
            LoginResponse response = new LoginResponse(token, savedUser.getUsername());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Error al registrar usuario: " + e.getMessage());
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                
                if (jwtUtil.validateToken(token)) {
                    String username = jwtUtil.extractUsername(token);
                    return ResponseEntity.ok("Token válido para usuario: " + username);
                }
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido: " + e.getMessage());
        }
    }
}