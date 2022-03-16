package io.itamarc.mymovieslistapi.transfer;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import io.itamarc.mymovieslistapi.model.AuthProvider;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPayload {
    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private Long id;

    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private String name;

    @JsonView(value = { UserViews.UserBasic.class })
    private String email;

    private String password;

    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private String imageUrl;

    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private LocalDateTime registered;

    private AuthProvider provider;

    private String providerId;

    @JsonView(UserViews.UserWithMoviesLists.class)
    @Builder.Default
    private Set<MoviesListPayload> moviesLists = new HashSet<>();
}
