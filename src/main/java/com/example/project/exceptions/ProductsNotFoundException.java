package com.example.project.exceptions;

public class ProductsNotFoundException extends Exception{
    public ProductsNotFoundException(String message) {
        super(message);
    }
}
