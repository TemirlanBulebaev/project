package com.example.project.repositories;

import com.example.project.entities.GreenCoffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GreenCoffeeRepository extends JpaRepository<GreenCoffee, Long> {
}
