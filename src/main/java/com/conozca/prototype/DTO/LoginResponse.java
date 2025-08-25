package com.conozca.prototype.DTO;

import com.conozca.prototype.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginResponse {
    private Integer id;
    private String username;
    private String email;
    private String privilege; // si aplica

    public LoginResponse(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.privilege = user.getPrivilege(); // opcional
    }

    // Getters
}