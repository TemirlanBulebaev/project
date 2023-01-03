package com.example.project.repositories;

import com.example.project.entities.UserDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserDeliveryRepository extends JpaRepository<UserDelivery, Long> {
    List<UserDelivery> findAllByUserInventoryID(Long userInventoryID);
    UserDelivery findUserDeliveryById(Long deliveryId);
}
