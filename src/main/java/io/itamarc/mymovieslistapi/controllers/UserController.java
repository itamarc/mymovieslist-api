package io.itamarc.mymovieslistapi.controllers;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.itamarc.mymovieslistapi.exception.ResourceNotFoundException;
import io.itamarc.mymovieslistapi.security.CurrentUser;
import io.itamarc.mymovieslistapi.security.UserPrincipal;
import io.itamarc.mymovieslistapi.services.UserService;
import io.itamarc.mymovieslistapi.transfer.UserPayload;
import io.itamarc.mymovieslistapi.transfer.UserViews;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @JsonView(UserViews.UserWithMoviesLists.class)
    public Set<UserPayload> getAllUsers() {
        log.debug("Mapping: Getting all users.");
        return userService.findAll();
    }

    @GetMapping("/users/{id}")
    @JsonView(UserViews.UserWithMoviesLists.class)
    public @ResponseBody UserPayload getUserPayloadById(@PathVariable Long id) {
        log.debug("Mapping: Getting user payload by id: " + id);
        return userService.findById(id);
    }

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    @JsonView(UserViews.UserBasic.class)
    public UserPayload getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        UserPayload userPayload = userService.findById(userPrincipal.getId());
        if (userPayload == null) {
            throw new ResourceNotFoundException("User", "id", userPrincipal.getId());
        }
        return userPayload;
    }
}
