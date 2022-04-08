package io.itamarc.mymovieslistapi.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.itamarc.mymovieslistapi.model.MoviesList;

public interface MoviesListRepository extends PagingAndSortingRepository<MoviesList, Long> {
}
