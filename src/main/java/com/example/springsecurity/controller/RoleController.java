package com.example.springsecurity.controller;

import com.example.springsecurity.Entity.Roles;
import com.example.springsecurity.service.RoleImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {
    @Autowired
    private RoleImplementation roleImplementation;
    @PostMapping("/roles/add")
    public Roles addRole(@RequestBody Roles role){
     return  roleImplementation.addRole(role);
    }
}
