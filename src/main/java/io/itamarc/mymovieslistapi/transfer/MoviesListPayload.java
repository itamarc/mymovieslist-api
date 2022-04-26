package io.itamarc.mymovieslistapi.transfer;

import java.util.Set;
import java.time.LocalDateTime;
import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(exclude = { "user", "movies" })
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoviesListPayload {
    @JsonView(value = { UserViews.UserWithMoviesLists.class, MoviesListViews.MoviesListBasic.class })
    private Long id;

    @JsonView(value = { UserViews.UserWithMoviesLists.class, MoviesListViews.MoviesListBasic.class })
    private String title;

    @JsonView(value = { UserViews.UserWithMoviesLists.class, MoviesListViews.MoviesListBasic.class })
    private LocalDateTime created;

    @JsonView(value = { UserViews.UserWithMoviesLists.class, MoviesListViews.MoviesListBasic.class })
    private LocalDateTime updated;

    @JsonView(value = { MoviesListViews.MoviesListBasic.class })
    @ToString.Exclude
    private UserPayload user;

    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    @Builder.Default
    private Set<MoviePayload> movies = new HashSet<>();

    @JsonView(value = { MoviesListViews.MoviesListBasic.class })
    private int moviesCount;

    @Override
    public String toString() {
        return "MoviesListPayload [id=" + id
                + ", title=" + title
                + ", created=" + created
                + ", updated=" + updated
                + ", user.id=" + user.getId()
                + ", moviesCount=" + moviesCount
                + ", movies=" + movies + "]";
    }
}
