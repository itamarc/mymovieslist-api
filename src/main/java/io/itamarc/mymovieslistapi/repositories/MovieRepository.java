package io.itamarc.mymovieslistapi.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import io.itamarc.mymovieslistapi.model.Movie;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    Optional<Movie> findByTitle(String title);
}
