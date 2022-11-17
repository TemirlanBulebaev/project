package com.example.project.payload;

public class ProductRequest {
    private String title;
    private String description;
    private Integer price;
    private String city;

    public ProductRequest() {

    }

    public ProductRequest(String title, String description, Integer price, String city) {
        this.title = title;
        this.description = description;
        this.price = price;
        this.city = city;
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

    @Override
    public String toString() {
        return "ProductRequest{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", city='" + city + '\'' +
                '}';
    }
}
