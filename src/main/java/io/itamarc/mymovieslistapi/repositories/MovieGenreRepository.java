package io.itamarc.mymovieslistapi.repositories;

import io.itamarc.mymovieslistapi.model.MovieGenre;
import org.springframework.data.repository.CrudRepository;

public interface MovieGenreRepository  extends CrudRepository<MovieGenre, Long> {
}
