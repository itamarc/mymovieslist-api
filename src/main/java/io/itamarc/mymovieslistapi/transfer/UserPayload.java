package io.itamarc.mymovieslistapi.transfer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserPayload {
    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private Long id;

    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private String name;

    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private String email;

    @JsonView(UserViews.UserRegister.class)
    private String password;

    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private String imageUrl;

    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private LocalDate registered;

    @JsonView(UserViews.UserWithMoviesLists.class)
    @Builder.Default
    private Set<MoviesListPayload> moviesLists = new HashSet<>();
}
