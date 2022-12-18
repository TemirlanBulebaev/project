package com.example.project.repositories;

import com.example.project.entities.InventoryUnit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryUnitRepository extends JpaRepository<InventoryUnit, Long> {
    InventoryUnit findInventoryUnitById(Long unitId);
    Long deleteInventoryUnitById(Long unitId);
}
