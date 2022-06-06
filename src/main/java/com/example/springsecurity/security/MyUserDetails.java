package com.example.springsecurity.security;

import com.example.springsecurity.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
public class MyUserDetails implements org.springframework.security.core.userdetails.UserDetails {
    private Collection<GrantedAuthority>authorities;
    private String password;
    private String username;
    private boolean active;
    private boolean enabled;

    public MyUserDetails(User user){
        username=user.getUsername();
        password = user.getPassword();
//        Stream api
       authorities = user.getRole()
                .stream()
                .map(x->new SimpleGrantedAuthority(x.getRoleName()))
                .collect(Collectors.toList());
        enabled = user.isEnabled();
        active = user.isLocked();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return authorities;


    }

    @Override
    public String getPassword() {
        return password;

    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
