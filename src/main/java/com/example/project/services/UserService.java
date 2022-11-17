package com.example.project.services;

import com.example.project.entities.User;
import com.example.project.exceptions.UserAlreadyExistException;
import com.example.project.exceptions.UserNotFoundException;
import com.example.project.models.UserResponse;
import com.example.project.payload.UserRequest;
import com.example.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User registration(UserRequest request) throws UserAlreadyExistException {
        if(userRepository.findByEmail(request.getEmail()) != null) {
            throw new UserAlreadyExistException("Пользователь с такой почтой уже существует!");
        }
        User user = new User (
                request.getEmail(),
                request.getPhoneNumber(),
                request.getUsername(),
                request.getPassword(),
                request.getDateOfCreated()
        );

        return userRepository.save(user);
    }

    public UserResponse getUser(Long id) throws UserNotFoundException {
        User user;
        if (userRepository.findById(id).isPresent()){
            user = userRepository.findById(id).get();
        } else {
            throw new UserNotFoundException("Пользователь не найден!");
        }
        return UserResponse.toModel(user);
    }

    public Long delete(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    public UserResponse editUser(UserResponse editUser, Long id) {
        User user = userRepository.findById(id).get();
        user.setUsername(editUser.getUsername());
        user.setEmail(editUser.getEmail());
        user.setPhoneNumber(editUser.getPhoneNumber());
        user.setPassword(editUser.getPassword());
        user.setActive(editUser.isActive());
        userRepository.save(user);
        return UserResponse.toModel(user);
    }
}
