package com.example.springsecurity.repo;

import com.example.springsecurity.Entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT s FROM User s where s.username =?1 OR s.email=?1")
    Optional<User> findUserByEmailOrUsername(String username);

}
