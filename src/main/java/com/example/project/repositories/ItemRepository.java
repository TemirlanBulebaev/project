package com.example.project.repositories;

import com.example.project.entities.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Set<Item> findAllByActive(boolean active);

    Page<Item> findAll (Pageable pageable);

}
