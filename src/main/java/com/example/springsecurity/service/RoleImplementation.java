package com.example.springsecurity.service;

import com.example.springsecurity.Entity.Roles;
import com.example.springsecurity.repo.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleImplementation implements RoleService{
    @Autowired
    private RolesRepository rolesRepository;
    @Override
    public Roles addRole(Roles role) {
        return rolesRepository.save(role);
    }
}
