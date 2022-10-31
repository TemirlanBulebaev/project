package com.example.project.services;

import com.example.project.entities.ProductEntity;
import com.example.project.entities.UserEntity;
import com.example.project.exceptions.NoEditProductException;
import com.example.project.exceptions.ProductNotFoundException;
import com.example.project.exceptions.ProductsNotFoundException;
import com.example.project.models.Product;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service// Является компонентом
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;


    public Product addNewProduct(ProductEntity product, Long userId) {
        UserEntity user = userRepository.findById(userId).get();
        product.setUser(user);
        return Product.toModel(productRepository.save(product));
    }

    public Product getProductInfo(Long id) throws ProductNotFoundException {
        ProductEntity product;
        if (productRepository.findById(id).isPresent()) {
            product = productRepository.findById(id).get();
        } else {
            throw new ProductNotFoundException("Товар не найден!");
        }
        return Product.toModel(product);
    }

    public List<Product> getAllProducts() throws ProductsNotFoundException {
        List<ProductEntity> products = productRepository.findAll();
        if (!products.isEmpty()) {
            return products.stream().map(Product::toModel).collect(Collectors.toList());
        } else {
            throw new ProductsNotFoundException("Товаров нет!");
        }
    }

    public Long deleteProducts(Long id) {
        productRepository.deleteById(id);
        return id;
    }

    public Product editProduct(ProductEntity editProduct, Long id){
        ProductEntity product = productRepository.findById(id).get();
        product.setTitle(editProduct.getTitle());
        product.setDescription(editProduct.getDescription());
        product.setPrice(editProduct.getPrice());
        product.setCity(editProduct.getCity());
        product.setDate(LocalDateTime.now());
        productRepository.save(product);
        return Product.toModel(product);
        }
}

