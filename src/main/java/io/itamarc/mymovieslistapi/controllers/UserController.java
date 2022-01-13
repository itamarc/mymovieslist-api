package io.itamarc.mymovieslistapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {
    UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/user")
    public Iterable<User> getAllUsers() {
        log.debug("Mapping: Getting all users.");
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Long id) {
        log.debug("Mapping: Getting user by id: " + id);
        return userRepository.findById(id).orElse(null);
    }
}
