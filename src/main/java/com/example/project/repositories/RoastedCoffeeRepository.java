package com.example.project.repositories;

import com.example.project.entities.RoastedCoffee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoastedCoffeeRepository extends JpaRepository<RoastedCoffee, Long> {
    RoastedCoffee findByName(String coffeeName);
    Boolean existsByName(String coffeeName);
}
