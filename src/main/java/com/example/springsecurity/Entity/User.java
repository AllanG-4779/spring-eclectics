package com.example.springsecurity.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long userId;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    @Column(nullable = false, length=60, unique = true)
    private String email;
    @Column(length = 60)
    private String password;
    @Column(name="active", nullable = false)
    private boolean enabled=false;
    @Column(name="locked", nullable = false)
    private boolean locked=true;
    @ManyToMany(targetEntity = Roles.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_role",
            joinColumns = @JoinColumn(name="user_id", referencedColumnName = "userId"),
            inverseJoinColumns = @JoinColumn(name="role_id", referencedColumnName = "roleId" )

    )

    private List<Roles> role;
}
