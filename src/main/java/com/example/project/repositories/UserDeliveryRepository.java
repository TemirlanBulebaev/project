package com.example.project.repositories;

import com.example.project.entities.UserDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDeliveryRepository extends JpaRepository<UserDelivery, Long> {
}
