package com.example.project.models;

import com.example.project.entities.Product;

public class ProductResponse {

    private String title;
    private String description;
    private Integer price;
    private String city;
    private String author;

    public static ProductResponse toModel(Product product) {
        ProductResponse model = new ProductResponse();
        model.setTitle(product.getTitle());
        model.setDescription(product.getDescription());
        model.setPrice(product.getPrice());
        model.setAuthor(product.getUser().getUsername());
        model.setCity(product.getCity());
        return model;
    }

    public ProductResponse() {
    }

    public ProductResponse(String title, String description, Integer price, String city, String author) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "ProductResponse{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", city='" + city + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}
