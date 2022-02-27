package io.itamarc.mymovieslistapi.controllers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import io.itamarc.mymovieslistapi.services.MoviesListService;
import io.itamarc.mymovieslistapi.transfer.MoviePayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListViews;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

public class MoviesListControllerTest {
    @Mock
    MoviesListService moviesListService;

    @InjectMocks
    MoviesListController moviesListController;

    MockMvc mockMvc;

    UserPayload user;
    MoviePayload movie1;
    MoviesListPayload moviesListPayload;


    @BeforeEach
    void setUp() {
        user = UserPayload.builder().id(1L).name("John Doe").email("johndoe@someweirdemail.cc").build();
        movie1 = MoviePayload.builder()
            .id(1L)
            .title("Movie 1")
            .description("Some movie 1")
            .imageUrl("/notfound.jpg")
            .rank(5)
            .watched(true)
            .build();
        moviesListPayload = MoviesListPayload.builder()
                .id(1L)
                .title("Sci-fi Movies")
                .user(user)
                .build();
        moviesListPayload.getMovies().add(movie1);
    }

    @Test
    public void getMoviesListByIdJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writerWithView(MoviesListViews.MoviesListWithMovies.class)
            .writeValueAsString(moviesListPayload);

        assertTrue(result.contains("Sci-fi Movies"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("johndoe@someweirdemail.cc"));
        assertTrue(result.contains("Movie 1"));

        assertFalse(result.contains("password"));
    }

    @Test
    public void getMoviesListWithMoviesByIdJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writerWithView(MoviesListViews.MoviesListBasic.class)
            .writeValueAsString(moviesListPayload);

        assertTrue(result.contains("Sci-fi Movies"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("johndoe@someweirdemail.cc"));

        assertFalse(result.contains("password"));
        assertFalse(result.contains("movies"));
    }
}
