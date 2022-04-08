package io.itamarc.mymovieslistapi.controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.itamarc.mymovieslistapi.services.MoviesListService;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListViews;
import io.itamarc.mymovieslistapi.transfer.PagedResponse;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class MoviesListController {
    private final MoviesListService moviesListsService;

    public MoviesListController(MoviesListService moviesListsService) {
        this.moviesListsService = moviesListsService;
    }

    @GetMapping("/movies-lists")
    @JsonView(MoviesListViews.MoviesListBasic.class)
    public PagedResponse<List<MoviesListPayload>> getMoviesLists(@RequestParam(required = false) Integer page) {
        log.debug("Mapping: Getting moviesLists (page=" + page + ")");
        if (page == null || page < 1) {
            page = 1;
        }
        Page<MoviesListPayload> dataPage = moviesListsService.getMoviesLists(page);
        PagedResponse<List<MoviesListPayload>> response = PagedResponse.<List<MoviesListPayload>>builder()
            .content(dataPage.getContent())
            .currentPage(dataPage.getNumber()+1)
            .pageSize(dataPage.getSize())
            .totalElements(dataPage.getTotalElements())
            .totalPages(dataPage.getTotalPages())
            .last(dataPage.isLast())
            .build();
        return response;
    }

    @GetMapping("/movies-lists/{id}")
    @JsonView(MoviesListViews.MoviesListBasic.class)
    public MoviesListPayload getMoviesListById(@PathVariable Long id) {
        log.debug("Mapping: Getting moviesList by id: " + id);
        return moviesListsService.findById(id);
    }

    @GetMapping("/movies-lists/{id}/movies")
    @JsonView(MoviesListViews.MoviesListWithMovies.class)
    public MoviesListPayload getMoviesListWithMoviesById(@PathVariable Long id) {
        log.debug("Mapping: Getting moviesList with movies by id: " + id);
        return moviesListsService.findById(id);
    }
}
