package com.example.springsecurity.controller;

import com.example.springsecurity.DTO.AuthDTO;
import com.example.springsecurity.DTO.UserDTO;
import com.example.springsecurity.Entity.User;
import com.example.springsecurity.models.UserModel;
import com.example.springsecurity.repo.UserRepository;
import com.example.springsecurity.security.CustomUserDetailsService;
import com.example.springsecurity.security.JwtUtility;
import com.example.springsecurity.security.MyUserDetails;
import com.example.springsecurity.service.ImplementUser;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.expression.ExpressionException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController

public class AuthController {
    @Autowired
    private JwtUtility utility;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ImplementUser userService;
    @Autowired
    private UserRepository userRepository;
    @PostMapping("/auth/reg")
    public UserDTO registerUser(@RequestBody UserModel user){
    System.out.println("The email is " + user.getEmail());
        return userService.registerUser(user);

    }
    @GetMapping("/")
    public List<UserDTO> getAllUsers(){
        return userService.getAll();
    }
    @PostMapping("/generate")
    public String generateToken(@RequestBody AuthDTO request) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password");
        }
        //load the user to encode
      User user =   userRepository.findUserByEmailOrUsername(request.getUsername()).orElseThrow(()->new ExpressionException("Something went wrong"));
      String token = utility.generateToken( new MyUserDetails(user));
      return token;



    }
}
