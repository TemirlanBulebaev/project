package com.example.project.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "active")
    private boolean active;


    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "user")
    private List<ProductEntity> products;

    @Column(name = "date_of_created")
    private LocalDateTime dateOfCreated;

    @PrePersist
    private void init() {
        dateOfCreated = LocalDateTime.now();
    }

}
