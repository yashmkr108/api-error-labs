package com.yash.api_error_lab.entity;

import com.yash.api_error_lab.enums.UserStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 155, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private Integer age;

    @Column(nullable = false)
    private String password;

    @Column(length = 20)
    private String phone;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(nullable = false, name = "created_at")
    private Instant createdAt;

    @Column(nullable = false, name = "updated_at")
    private Instant updatedAt;

    public User(String name, String email, Integer age, String phone,String password){
        this.name = name;
        this.email = email;
        this.age = age;
        this.phone = phone;
        this.password = password;

        this.status = UserStatus.ACTIVE;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
    }

    public void updateProfile(
            String name,
            String email,
            String phone,
            Integer age,
            String password
    ) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.age = age;
        this.password = password;
        this.updatedAt = Instant.now();
    }
}
