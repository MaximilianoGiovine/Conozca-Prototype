package com.conozca.prototype.DTO;

import com.conozca.prototype.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LoginResponse {
    private String token;
    private String username;
    private Integer id;
    private String email;
    private String privilege;

    // Constructor con token y username
    public LoginResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    // Constructor con User completo
    public LoginResponse(User user, String token) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.privilege = user.getPrivilege();
        this.token = token;
    }
}