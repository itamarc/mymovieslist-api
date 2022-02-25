package io.itamarc.mymovieslistapi.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.UserRepository;
import io.itamarc.mymovieslistapi.transfer.UserMoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

@Service  
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<User> findAll() {
        Set<User> users = new HashSet<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }

    @Override
    public UserPayload findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            UserPayload userPayload = new UserPayload();
            userPayload.setId(user.getId());
            userPayload.setName(user.getName());
            userPayload.setEmail(user.getEmail());
            userPayload.setPassword(user.getPassword());
            userPayload.setImageUrl(user.getImageUrl());
            userPayload.setRegistered(user.getRegistered());
            if (user.getMoviesLists() != null) {
                user.getMoviesLists().forEach(
                    moviesList -> userPayload.getMoviesLists().add(
                        UserMoviesListPayload.builder()
                        .id(moviesList.getId())
                        .title(moviesList.getTitle())
                        .created(moviesList.getCreated())
                        .updated(moviesList.getUpdated())
                        .build()));
            }
            return userPayload;
        } else {
            return null;
        }
    }
}
