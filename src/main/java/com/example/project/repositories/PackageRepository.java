package com.example.project.repositories;

import com.example.project.entities.Package;
import com.example.project.entities.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackageRepository extends JpaRepository<Package, Long> {

    Package findByName(PackageType packageType);
    Boolean existsByName(PackageType packageType);
}
