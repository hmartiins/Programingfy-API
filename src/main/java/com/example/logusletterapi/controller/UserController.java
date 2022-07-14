package com.example.logusletterapi.controller;

import com.example.logusletterapi.model.User;
import com.example.logusletterapi.repository.IUserRepository;
import com.example.logusletterapi.errorcontroller.EntityNotFoundException;
import com.example.logusletterapi.services.SESService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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

    @GetMapping(value = "/{userId}")
    public ResponseEntity<Optional<User>> get(@PathVariable("userId") Long userId) throws EntityNotFoundException {
        var user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new EntityNotFoundException(User.class, "id", userId.toString());
        }

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> list() throws EntityNotFoundException {
        var user = userRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping(path = "/sendEmail")
    public ResponseEntity sendEmail() throws EntityNotFoundException {
        SESService.sendMessage("LogusLetter - " + LocalDate.now());
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

}
