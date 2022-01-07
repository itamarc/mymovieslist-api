package io.itamarc.mymovieslistapi.repositories;

import org.springframework.data.repository.CrudRepository;

import io.itamarc.mymovieslistapi.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {
}
