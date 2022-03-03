package io.itamarc.mymovieslistapi.transfer;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MoviePayload {
    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    private Long id;

    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    private String title;

    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    private String description;

    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    private Integer year;

    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    private String imageUrl;

    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    private Integer rank;

    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    private boolean watched;
}
