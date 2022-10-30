package com.example.project.controllers;

import com.example.project.entities.UserEntity;
import com.example.project.exceptions.UserAlreadyExistException;
import com.example.project.exceptions.UserNotFoundException;
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
    public ResponseEntity registration(@RequestBody UserEntity user) {
        try{
            userService.registration(user);
            return ResponseEntity.ok("Пользователь успешно сохранен!");
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");

        }
    }

    @GetMapping("/info")
    public ResponseEntity getOneUser(@RequestParam Long id) {
        try{
            return ResponseEntity.ok(userService.getOne(id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка!");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        try {
            return ResponseEntity.ok(userService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }
}
