package io.itamarc.mymovieslistapi.services;

import java.util.Set;

import io.itamarc.mymovieslistapi.model.MoviesList;

public interface MoviesListService {
    public Set<MoviesList> getMoviesLists(int page);

    public MoviesList findById(Long id);
}
