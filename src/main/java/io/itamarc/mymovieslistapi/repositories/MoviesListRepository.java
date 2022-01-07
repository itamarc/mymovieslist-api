package io.itamarc.mymovieslistapi.repositories;

import org.springframework.data.repository.CrudRepository;

import io.itamarc.mymovieslistapi.model.MoviesList;

public interface MoviesListRepository extends CrudRepository<MoviesList, Long> {
}
