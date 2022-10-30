package com.example.project.models;

import com.example.project.entities.UserEntity;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class User {
    private Long id;
    private String email;
    private String phoneNumber;
    private String username;
    private boolean active;
    private List<Product> products;

    public static User toModel(UserEntity entity) {
        User model = new User();
        model.setId(entity.getId());
        model.setUsername(entity.getUsername());
        model.setEmail(entity.getEmail());
        model.setPhoneNumber(entity.getPhoneNumber());
        model.setActive(entity.isActive());
        model.setProducts(entity.getProducts().stream().map(Product:: toModel).collect(Collectors.toList()));
        return model;
    }
}
