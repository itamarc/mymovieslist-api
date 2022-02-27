package io.itamarc.mymovieslistapi.transfer;

import java.time.LocalDate;
import java.util.Set;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoviesListPayload {
    @JsonView(value = { UserViews.UserWithMoviesLists.class, MoviesListViews.MoviesListBasic.class })
    private Long id;

    @JsonView(value = { UserViews.UserWithMoviesLists.class, MoviesListViews.MoviesListBasic.class })
    private String title;

    @JsonView(value = { UserViews.UserWithMoviesLists.class, MoviesListViews.MoviesListBasic.class })
    private LocalDate created;

    @JsonView(value = { UserViews.UserWithMoviesLists.class, MoviesListViews.MoviesListBasic.class })
    private LocalDate updated;

    @JsonView(value = { MoviesListViews.MoviesListBasic.class })
    private UserPayload user;

    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    @Builder.Default
    private Set<MoviePayload> movies = new HashSet<>();
}
