package com.example.project.services;

import com.example.project.entities.UserEntity;
import com.example.project.exceptions.UserAlreadyExistException;
import com.example.project.exceptions.UserNotFoundException;
import com.example.project.models.User;
import com.example.project.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserEntity registration(UserEntity user) throws UserAlreadyExistException {
        if(userRepository.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistException("Пользователь с такой почтой уже существует!");
        }
        return userRepository.save(user);
    }

    public User getOne(Long id) throws UserNotFoundException {
        UserEntity user;
        if (userRepository.findById(id).isPresent()){
            user = userRepository.findById(id).get();
        } else {
            throw new UserNotFoundException("Пользователь не найден!");
        }
        return User.toModel(user);
    }

    public Long delete(Long id) {
        userRepository.deleteById(id);
        return id;
    }

    public User editUser(UserEntity editUser, Long id) {
        UserEntity user = userRepository.findById(id).get();
        user.setUsername(editUser.getUsername());
        user.setEmail(editUser.getEmail());
        user.setPhoneNumber(editUser.getPhoneNumber());
        user.setPassword(editUser.getPassword());
        user.setActive(editUser.isActive());
        userRepository.save(user);
        return User.toModel(user);
    }
}
