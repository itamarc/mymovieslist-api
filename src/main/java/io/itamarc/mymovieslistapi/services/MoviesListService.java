package io.itamarc.mymovieslistapi.services;

import org.springframework.data.domain.Page;

import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;

public interface MoviesListService {
    public Page<MoviesListPayload> getMoviesLists(int page);

    public MoviesListPayload findById(Long id);

    MoviesListPayload newMoviesList(MoviesListPayload moviesList);
}
