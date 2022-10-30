package com.example.project.controllers;

import com.example.project.entities.ProductEntity;
import com.example.project.exceptions.ProductNotFoundException;
import com.example.project.exceptions.ProductsNotFoundException;
import com.example.project.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor // Заменяет создание конструктора
public class ProductController {
    private final ProductService productService;//При создании бина, он будет сразу инжектиться
    @PostMapping("/create")
    public ResponseEntity addProduct(@RequestBody ProductEntity product,
                                     @RequestParam Long userId) {
        try {
            return ResponseEntity.ok(productService.addNewProduct(product, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/info")
    public ResponseEntity getProduсtInfo(@RequestParam Long productId) {
        try {
            return ResponseEntity.ok(productService.getProductInfo(productId));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @GetMapping("/all")
    public ResponseEntity getAllProducts(){
        try {
            return ResponseEntity.ok(productService.getAllProducts());
        } catch (ProductsNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProduct(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(productService.deleteProducts(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }


//    @PutMapping
//    public ResponseEntity isForSaleProduct(@RequestParam Long id) {
//        try {
//            return ResponseEntity.ok(productService.isForSale(id));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }
}
