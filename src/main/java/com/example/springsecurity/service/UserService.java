package com.example.springsecurity.service;

import com.example.springsecurity.DTO.UserDTO;
import com.example.springsecurity.models.UserModel;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    UserDTO registerUser(UserModel userModel);
    List<UserDTO> getAll();
}
