package com.example.springsecurity.service;

import com.example.springsecurity.DTO.UserDTO;
import com.example.springsecurity.Entity.Roles;
import com.example.springsecurity.Entity.User;
import com.example.springsecurity.models.UserModel;
import com.example.springsecurity.repo.RolesRepository;
import com.example.springsecurity.repo.UserRepository;

import com.example.springsecurity.security.PasswordConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImplementUser implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordConfig passwordConfig;

    @Autowired
    private RolesRepository rolesRepository;


    public UserDTO registerUser(UserModel userModel){


        //Build the role
         Roles role1 = rolesRepository.findById(Long.valueOf(2)).get();
//        rolesRepository.saveAll(List.of(role1, role2));
        if (role1 == null){
            throw new RuntimeException("Please add the role first");
        }
        List <Roles> roles = new ArrayList<>();
        roles.add(role1);


        //set the entity attributes;
//        if (userModel.getEmail().isEmpty() || userModel.getLastName().isEmpty()
//        || userModel.getPassword().isEmpty() || userModel.getFirstName().isEmpty()
//        ){
//            throw new RuntimeException("All fields are required");
//        }
        User user = User.builder()
                .email(userModel.getEmail())
                .firstName(userModel.getFirstName())
                .lastName(userModel.getLastName())
                .username(userModel.getUsername())
                .password(passwordConfig.passwordEncoder().encode( userModel.getPassword()))
                .role(roles)
                .locked(false)
                .enabled(true)
                .build();



         userRepository.save(user);

        return UserDTO.builder().
                firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    @Override
    public List<UserDTO> getAll() {
        List<User> users =  userRepository.findAll();
        List <UserDTO> returnUsers = new ArrayList<>();
        users.forEach((user)->returnUsers.add(UserDTO.builder()
                        .email(user.getEmail())
                        .firstName(user.getFirstName())
                        .lastName(user.getLastName())
                        .username(user.getUsername())
                .build()));
        return returnUsers;
    }


}