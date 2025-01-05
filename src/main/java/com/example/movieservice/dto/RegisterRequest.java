package com.example.movieservice.dto;

import com.example.movieservice.model.User;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String email;
    private String password;

    public User toUser() {
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}