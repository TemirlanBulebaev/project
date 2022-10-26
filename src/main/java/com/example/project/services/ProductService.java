package com.example.project.services;

import com.example.project.entities.ProductEntity;
import com.example.project.entities.UserEntity;
import com.example.project.models.Product;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    public Product addNewProduct(ProductEntity product, Long userId) {
        UserEntity user = userRepository.findById(userId).get();
        product.setUser(user);
        return Product.toModel(productRepository.save(product));
    }

    public Product isForSale(Long id) {
        ProductEntity product = productRepository.findById(id).get();
        product.setForSale(!product.getForSale());
        return Product.toModel(productRepository.save(product));
    }
}
