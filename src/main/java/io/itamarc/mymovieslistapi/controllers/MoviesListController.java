package io.itamarc.mymovieslistapi.controllers;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.services.MoviesListService;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MoviesListController {
    private final MoviesListService moviesListsService;

    public MoviesListController(MoviesListService moviesListsService) {
        this.moviesListsService = moviesListsService;
    }

    @GetMapping("/moviesLists")
    public Set<MoviesList> getMoviesLists(@RequestParam(required = false) Integer page) {
        log.debug("Mapping: Getting moviesLists (page=" + page + ")");
        if (page == null || page < 1) {
            page = 1;
        }
        return moviesListsService.getMoviesLists(page);
    }

    @GetMapping("/moviesList/{id}")
    public MoviesList getMoviesListById(@PathVariable Long id) {
        log.debug("Mapping: Getting moviesList by id: " + id);
        return moviesListsService.findById(id);
    }
}
