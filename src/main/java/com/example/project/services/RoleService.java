package com.example.project.services;

import com.example.project.entities.Role;
import com.example.project.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;

public class RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Получение списка всех ролей
     * @return
     */
    public Collection<Role> findAll() {
        return roleRepository.findAll();
    }
}
