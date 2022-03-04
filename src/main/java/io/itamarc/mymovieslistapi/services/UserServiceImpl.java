package io.itamarc.mymovieslistapi.services;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.UserRepository;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Set<UserPayload> findAll() {
        Set<UserPayload> users = new LinkedHashSet<>();
        userRepository.findAll().forEach(user -> users.add(userToUserPayload(user)));
        return users;
    }

    @Override
    public UserPayload findById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return userToUserPayload(user);
        } else {
            return null;
        }
    }

    private UserPayload userToUserPayload(User user) {
        UserPayload userPayload = UserPayload.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .imageUrl(user.getImageUrl())
                .registered(user.getRegistered())
                .build();
                if (!user.getMoviesLists().isEmpty()) {
                    user.getMoviesLists().forEach(
                        moviesList -> userPayload.getMoviesLists().add(
                            MoviesListPayload.builder()
                            .id(moviesList.getId())
                            .title(moviesList.getTitle())
                            .created(moviesList.getCreated())
                            .updated(moviesList.getUpdated())
                            .build()));
                }
        return userPayload;
    }

    private User userPayloadToUser(UserPayload userPayload) {
        User user = new User();
        user.setId(userPayload.getId());
        user.setName(userPayload.getName());
        user.setEmail(userPayload.getEmail());
        user.setPassword(userPayload.getPassword());
        user.setImageUrl(userPayload.getImageUrl());
        user.setRegistered(userPayload.getRegistered());
        user.setProvider(userPayload.getProvider());
        return user;
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public UserPayload save(UserPayload user) {
        User userSaved = userRepository.save(userPayloadToUser(user));
        return userToUserPayload(userSaved);
    }
}
