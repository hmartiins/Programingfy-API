package com.example.programingfyapi.controller;

import com.example.programingfyapi.model.User;
import com.example.programingfyapi.repository.IUserRepository;
import com.example.programingfyapi.errorcontroller.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private final IUserRepository userRepository;

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User newUser) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(newUser));
    }

    public ResponseEntity<User> get(@RequestBody User newUser) throws EntityNotFoundException {
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(newUser));
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Optional<User>> getBirdNoException(@PathVariable("userId") Long userId) throws EntityNotFoundException {
        var user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new EntityNotFoundException(User.class, "id", userId.toString());
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

}
