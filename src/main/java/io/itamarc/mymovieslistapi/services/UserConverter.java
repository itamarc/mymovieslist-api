package io.itamarc.mymovieslistapi.services;

import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

public class UserConverter {
    public static UserPayload userToUserPayload(User user) {
        UserPayload userPayload = UserPayload.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .imageUrl(user.getImageUrl())
                .registered(user.getRegistered())
                .emailVerified(user.getEmailVerified())
                .build();
        if (!user.getMoviesLists().isEmpty()) {
            user.getMoviesLists().forEach(
                    moviesList -> userPayload.getMoviesLists().add(
                            MoviesListConverter.moviesListToMoviesListPayload(moviesList, userPayload)
                    )
            );
        }
        return userPayload;
    }

    public static User userPayloadToUser(UserPayload userPayload) {
        User user = new User();
        user.setId(userPayload.getId());
        user.setName(userPayload.getName());
        user.setEmail(userPayload.getEmail());
        user.setPassword(userPayload.getPassword());
        user.setImageUrl(userPayload.getImageUrl());
        user.setRegistered(userPayload.getRegistered());
        user.setProvider(userPayload.getProvider());
        user.setEmailVerified(userPayload.getEmailVerified());
        if (!userPayload.getMoviesLists().isEmpty()) {
            userPayload.getMoviesLists().forEach(
                moviesListPayload -> {
                    user.getMoviesLists().add(
                        MoviesListConverter.moviesListPayloadToMoviesList(moviesListPayload, user)
                    );
                }
            );
        }
        return user;
    }
}
