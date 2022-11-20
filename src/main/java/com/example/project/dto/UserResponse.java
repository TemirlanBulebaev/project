package com.example.project.dto;

import com.example.project.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserResponse {
    private Long id;
    private String email;
    private String phoneNumber;
    private String username;

    private String password;
    private boolean active;
    private List<ProductResponse> products;

//    public static UserResponse toModel(User user) {
//        UserResponse model = new UserResponse();
//        model.setUsername(user.getUsername());
//        model.setEmail(user.getEmail());
//        model.setPhoneNumber(user.getPhoneNumber());
//        model.setActive(user.isActive());
//        model.setProducts(user.getProducts().stream().map(ProductResponse::toModel).collect(Collectors.toList()));
//        return model;
//    }

    public UserResponse() {
    }

    public UserResponse(Long id, String email, String phoneNumber, String username, boolean active, List<ProductResponse> products) {
        this.id = id;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.active = active;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<ProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<ProductResponse> products) {
        this.products = products;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserResponse{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", username='" + username + '\'' +
                ", active=" + active +
                ", products=" + products +
                '}';
    }

}
