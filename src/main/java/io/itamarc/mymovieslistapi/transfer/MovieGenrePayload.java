package io.itamarc.mymovieslistapi.transfer;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieGenrePayload {
    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    private Long id;

    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    private String name;
}
