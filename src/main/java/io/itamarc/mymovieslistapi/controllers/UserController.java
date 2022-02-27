package io.itamarc.mymovieslistapi.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.services.UserService;
import io.itamarc.mymovieslistapi.transfer.UserPayload;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public Set<User> getAllUsers() {
        log.debug("Mapping: Getting all users.");
        return userService.findAll();
    }

    @GetMapping("/user/{id}")
    public @ResponseBody UserPayload getUserPayloadById(@PathVariable Long id) {
        log.debug("Mapping: Getting user payload by id: " + id);
        return userService.findById(id);
    }
}