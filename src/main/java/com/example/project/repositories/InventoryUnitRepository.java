package com.example.project.repositories;

import com.example.project.entities.InventoryUnit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface InventoryUnitRepository extends JpaRepository<InventoryUnit, Long> {
    InventoryUnit findInventoryUnitById(Long unitId);
    Long deleteInventoryUnitById(Long unitId);

    Set<InventoryUnit> findInventoryUnitsByUserInventoryId(Long id);
}
