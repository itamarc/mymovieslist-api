package io.itamarc.mymovieslistapi.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.repositories.MoviesListRepository;

@Service
public class MoviesListServiceImpl implements MoviesListService {
    private final MoviesListRepository moviesListRepository;

    public MoviesListServiceImpl(MoviesListRepository moviesListRepository) {
        this.moviesListRepository = moviesListRepository;
    }

    public Set<MoviesList> getMoviesLists(int page) {
        Set<MoviesList> moviesLists = new HashSet<>();
        if (page < 1) {
            page = 1;
        }
        // TODO get only a page of movies lists
        Iterable<MoviesList> iter = moviesListRepository.findAll();
        iter.forEach(list -> moviesLists.add(list));
        return moviesLists;
    }

    public MoviesList findById(Long id) {
        Optional<MoviesList> optional = moviesListRepository.findById(id);
        return optional.orElse(null);
    }
}
