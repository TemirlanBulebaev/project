package com.example.project.repositories;

import com.example.project.entities.ProductEntity;
import com.example.project.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    @Override
    List<ProductEntity> findAll();
}
