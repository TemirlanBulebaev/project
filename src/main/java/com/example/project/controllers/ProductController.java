package com.example.project.controllers;

import com.example.project.entities.ProductEntity;
import com.example.project.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity addProduct(@RequestBody ProductEntity product,
                                     @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(productService.addNewProduct(product, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @PutMapping
    public ResponseEntity isForSaleProduct(@RequestParam Long id) {
        try {
            return ResponseEntity.ok(productService.isForSale(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
