package com.example.springsecurity.controller;

import com.example.springsecurity.DTO.UserDTO;
import com.example.springsecurity.models.UserModel;
import com.example.springsecurity.service.ImplementUser;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class AuthController {
    @Autowired
    private ImplementUser userService;
    @PostMapping("/auth/reg")
    public UserDTO registerUser(@RequestBody UserModel user){
    System.out.println("The email is " + user.getEmail());
        return userService.registerUser(user);

    }
    @GetMapping("/")
    public List<UserDTO> getAllUsers(){
        return userService.getAll();
    }
}
