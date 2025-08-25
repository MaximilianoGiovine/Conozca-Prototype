package com.conozca.prototype.service;

import com.conozca.prototype.model.User;

import java.util.List;

public interface IUserService {
    List<User> listarUsuarios();

    User buscarUserPorId(Integer user_id);

    User guardarUsuario (User user);

    void eliminarUserPorId (Integer user_id);
}
