package io.itamarc.mymovieslistapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.repositories.MoviesListRepository;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MoviesListController {
    MoviesListRepository moviesListRepository;

    public MoviesListController(MoviesListRepository moviesListRepository) {
        this.moviesListRepository = moviesListRepository;
    }

    @GetMapping("/moviesList/{id}")
    public MoviesList getMoviesListById(@PathVariable Long id) {
        log.debug("Mapping: Getting moviesList by id: " + id);
        return moviesListRepository.findById(id).orElse(null);
    }

    
}
