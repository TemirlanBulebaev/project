package com.example.project.dto;

import com.example.project.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserProfileDto {

    private Long id;
    private String username;
    private String email;

    public UserProfileDto() {
    }

    public UserProfileDto(Long id,
                          String username,
                          String email,
                          String balance) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    public User toUser () {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }

    public static UserProfileDto fromUser (User user) {
        UserProfileDto userDto = new UserProfileDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", username='" + getUsername() + "'" +
                ", email='" + getEmail() + "'" +
                "}";
    }

}