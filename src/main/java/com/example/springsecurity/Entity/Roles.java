package com.example.springsecurity.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Roles {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
        private Long roleId;
   @Column(unique = true, nullable = false)
        private String roleName;

}
