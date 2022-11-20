//package com.example.project.controllers;
//
//import com.example.project.exceptions.ProductNotFoundException;
//import com.example.project.dto.ProductResponse;
//import com.example.project.payload.ProductRequest;
//import com.example.project.services.ProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/product")
//public class ProductController {
//    @Autowired
//    private ProductService productService;
//
//    @PostMapping("/create")
//    public ResponseEntity addProduct(@RequestBody ProductRequest request,
//                                     @RequestParam Long userId) {
//        try {
//            return ResponseEntity.ok(productService.addNewProduct(request, userId));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }
//
//    @GetMapping("/{id}/info")
//    public ResponseEntity getProduсtInfo(@PathVariable Long productId) {
//        try {
//            return ResponseEntity.ok(productService.getProductInfo(productId));
//        } catch (ProductNotFoundException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }
//
//    @GetMapping("/all")
//    public ResponseEntity getAllProducts() {
//        try {
//            return ResponseEntity.ok(productService.getAllProducts());
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }
//
//    @DeleteMapping("/{id}/delete")
//    public ResponseEntity deleteProduct(@PathVariable Long id) {
//        try {
//            return ResponseEntity.ok(productService.deleteProducts(id));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }
//
//
//    @PutMapping("/{id}/edit")
//    public ResponseEntity editProduct(@RequestBody ProductResponse editProduct,
//                                      @PathVariable Long id) {
//        try {
//            return ResponseEntity.ok(productService.editProduct(editProduct, id));
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body("Произошла ошибка");
//        }
//    }
//}
//
