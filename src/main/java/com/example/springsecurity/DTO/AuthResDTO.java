package com.example.springsecurity.DTO;

import com.example.springsecurity.Entity.User;
import lombok.Data;

@Data
public class AuthResDTO {
    private String email;
    private String username;
    private String jwt;

    public AuthResDTO(User user, String token){
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.jwt = token;

    }
}

