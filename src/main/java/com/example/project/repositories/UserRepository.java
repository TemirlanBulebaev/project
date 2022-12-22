package com.example.project.repositories;


import com.example.project.entities.User;
import com.example.project.entities.UserInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email); // поиск по существующему майлу

    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    List<User> findByActive (Boolean acttive);
}
