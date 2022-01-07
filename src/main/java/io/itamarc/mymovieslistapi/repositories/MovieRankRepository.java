package io.itamarc.mymovieslistapi.repositories;

import org.springframework.data.repository.CrudRepository;

import io.itamarc.mymovieslistapi.model.MovieRank;

public interface MovieRankRepository extends CrudRepository<MovieRank, Long> {
}
