package com.example.programingfyapi.controller;

import com.example.programingfyapi.model.User;
import com.example.programingfyapi.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User newUser) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(newUser));
    }

}
