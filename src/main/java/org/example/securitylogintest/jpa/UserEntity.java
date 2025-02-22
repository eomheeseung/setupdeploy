package org.example.securitylogintest.jpa;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String password;
    private String name;

}
