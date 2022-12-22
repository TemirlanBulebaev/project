package com.example.project.repositories;

import com.example.project.entities.UserInventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInventoryRepository extends JpaRepository<UserInventory, Long> {

}
