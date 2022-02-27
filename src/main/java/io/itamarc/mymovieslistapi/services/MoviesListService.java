package io.itamarc.mymovieslistapi.services;

import java.util.Set;

import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;

public interface MoviesListService {
    public Set<MoviesListPayload> getMoviesLists(int page);

    public MoviesListPayload findById(Long id);
}
