package com.example.project.services;

import com.example.project.entities.Product;
import com.example.project.entities.User;
import com.example.project.exceptions.ProductNotFoundException;
import com.example.project.exceptions.ProductsNotFoundException;
import com.example.project.models.ProductResponse;
import com.example.project.payload.ProductRequest;
import com.example.project.repositories.ProductRepository;
import com.example.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;


    public ProductResponse addNewProduct(ProductRequest request, Long userId) {

        User user = userRepository.findById(userId).get();
        // Из JWT user находит настоящго юзра (JwtUser -> User)

        Product product = new Product(
                request.getTitle(),
                request.getDescription(),
                request.getPrice(),
                request.getCity(),
                user
        );
        return ProductResponse.toModel(productRepository.save(product));
    }

    public ProductResponse getProductInfo(Long id) throws ProductNotFoundException {
        Product product;
        if (productRepository.findById(id).isPresent()) {
            product = productRepository.findById(id).get();
        } else {
            throw new ProductNotFoundException("Товар не найден!");
        }
        return ProductResponse.toModel(product);
    }

    public List<ProductResponse> getAllProducts() throws ProductsNotFoundException {
        List<Product> products = productRepository.findAll();
        if (!products.isEmpty()) {
            return products.stream().map(ProductResponse::toModel).collect(Collectors.toList());
        } else {
            throw new ProductsNotFoundException("Товаров нет!");
        }
    }

    public Long deleteProducts(Long id) {
        productRepository.deleteById(id);
        return id;
    }

    public ProductResponse editProduct(ProductResponse editProduct, Long id){
        Product product = productRepository.findById(id).get();
        product.setTitle(editProduct.getTitle());
        product.setDescription(editProduct.getDescription());
        product.setPrice(editProduct.getPrice());
        product.setCity(editProduct.getCity());
        product.setDate(LocalDateTime.now());
        productRepository.save(product);
        return ProductResponse.toModel(product);
        }
}

