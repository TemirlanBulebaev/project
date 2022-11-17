package com.example.project.controllers;

import com.example.project.entities.User;
import com.example.project.exceptions.UserAlreadyExistException;
import com.example.project.exceptions.UserNotFoundException;
import com.example.project.models.UserResponse;
import com.example.project.payload.UserRequest;
import com.example.project.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registration")
    public ResponseEntity registration(@RequestBody UserRequest request) {
        try {
            userService.registration(request);
            return ResponseEntity.ok("Пользователь успешно сохранен!");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");

        }
    }

    @GetMapping("/{id}/info")
    public ResponseEntity getUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUser(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity editUser(@RequestBody UserResponse editUser,
                                   @PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.editUser(editUser, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
