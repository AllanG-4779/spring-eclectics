package com.example.springsecurity.security;

import com.example.springsecurity.Entity.User;
import com.example.springsecurity.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;



    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

     User user = userRepository.findUserByEmailOrUsername(username).get();

     if (user.getUsername() == null){
         throw new UsernameNotFoundException(String.format("username %s not found", username));
     }




      return new MyUserDetails(user);
    }
}
