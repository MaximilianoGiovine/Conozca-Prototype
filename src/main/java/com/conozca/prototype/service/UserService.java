package com.conozca.prototype.service;

import com.conozca.prototype.exception.*;
import com.conozca.prototype.model.User;
import com.conozca.prototype.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<User> listarUsuarios() {
        return userRepository.findAll();
    }

    @Override
    public User buscarUserPorId(Integer user_id) {
        return userRepository.findById(user_id)
                .orElseThrow(() -> new UserNotFoundException("El usuario con id " + user_id + " no existe"));
    }

    @Override
    public User guardarUsuario(User user) {
        if (user == null) {
            throw new MissingFieldException("El usuario no puede ser nulo");
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new MissingFieldException("El email es obligatorio");
        }
        if (!user.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new InvalidEmailFormatException("El email no tiene un formato válido");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistsException("El email ya está registrado");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new MissingFieldException("La contraseña es obligatoria");
        }
        if (user.getPassword().length() < 8) {
            throw new WeakPasswordException("La contraseña es demasiado débil");
        }
        // Aquí podrías agregar más validaciones de contraseña (mayúsculas, números, etc.)

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void eliminarUserPorId(Integer user_id) {
        if (!userRepository.existsById(user_id)) {
            throw new UserNotFoundException("El usuario con id " + user_id + " no existe");
        }
        userRepository.deleteById(user_id);
    }
    public User login(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("No existe un usuario con el nombre de usuario " + username));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new InvalidCredentialsException("Las credenciales son incorrectas");
        }

        return user;
    }
}