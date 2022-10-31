package com.example.project.models;

import com.example.project.entities.ProductEntity;
import com.example.project.entities.UserEntity;
import com.example.project.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long id;
    private String title;
    private String description;
    private Integer price;
    private String city;
    private String author;
    private LocalDateTime date;

    public static Product toModel(ProductEntity entity) {
        Product model = new Product();
        model.setId(entity.getId());
        model.setTitle(entity.getTitle());
        model.setDescription(entity.getDescription());
        model.setPrice(entity.getPrice());
        model.setAuthor(entity.getUser().getUsername());
        model.setCity(entity.getCity());
        model.setDate(entity.getDate());
        return model;
    }
}
