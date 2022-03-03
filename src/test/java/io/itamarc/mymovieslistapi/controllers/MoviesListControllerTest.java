package io.itamarc.mymovieslistapi.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io.itamarc.mymovieslistapi.services.MoviesListService;
import io.itamarc.mymovieslistapi.transfer.MoviePayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

public class MoviesListControllerTest {
    @Mock
    MoviesListService moviesListService;

    MoviesListController moviesListController;

    MockMvc mockMvc;

    UserPayload user;
    MoviePayload movie1;
    MoviesListPayload moviesListPayload;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        moviesListController = new MoviesListController(moviesListService);

        mockMvc = MockMvcBuilders.standaloneSetup(moviesListController).build();
        user = UserPayload.builder()
                .id(1L)
                .name("John Doe")
                .email("johndoe@someweirdemail.cc")
                .build();
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

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }



    @Test
    public void getMoviesListById() throws Exception {
        // given
        when(moviesListService.findById(anyLong())).thenReturn(moviesListPayload);
        // when
        mockMvc.perform(get("/movies-lists/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("Sci-fi Movies")))
            .andExpect(jsonPath("$.user.id", is(1)))
            .andExpect(jsonPath("$.user.name", is("John Doe")))
            .andExpect(jsonPath("$.user.email", is("johndoe@someweirdemail.cc")))
            .andExpect(jsonPath("$.movies").doesNotExist());
        // then
        verify(moviesListService, times(1)).findById(anyLong());
    }

    @Test
    public void getMoviesListWithMoviesById() throws Exception {
        // given
        when(moviesListService.findById(anyLong())).thenReturn(moviesListPayload);
        // when
        mockMvc.perform(get("/movies-lists/1/movies"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(1)))
            .andExpect(jsonPath("$.title", is("Sci-fi Movies")))
            .andExpect(jsonPath("$.user.id", is(1)))
            .andExpect(jsonPath("$.user.name", is("John Doe")))
            .andExpect(jsonPath("$.user.email", is("johndoe@someweirdemail.cc")))
            .andExpect(jsonPath("$.movies", hasSize(1)));
        // then
        verify(moviesListService, times(1)).findById(anyLong());    }
}
