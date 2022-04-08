package io.itamarc.mymovieslistapi.controllers;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    Page<MoviesListPayload> moviesLists;

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

    // @GetMapping("/movies-lists")
    // @JsonView(MoviesListViews.MoviesListBasic.class)
    // public Set<MoviesListPayload> getMoviesLists(@RequestParam(required = false) Integer page) {
    //     log.debug("Mapping: Getting moviesLists (page=" + page + ")");
    //     if (page == null || page < 1) {
    //         page = 1;
    //     }
    //     return moviesListsService.getMoviesLists(page);
    // }

    @Test
    public void getMoviesLists() throws Exception {
        // given
        MoviesListPayload moviesListPayload2 = MoviesListPayload.builder()
                                                .id(2L)
                                                .title("Action Movies")
                                                .user(user)
                                                .build();

        moviesLists = new PageImpl<MoviesListPayload>(Arrays.asList(moviesListPayload, moviesListPayload2));

        when(moviesListService.getMoviesLists(anyInt())).thenReturn(moviesLists);

        // when
        mockMvc.perform(get("/movies-lists"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("Sci-fi Movies")))
            .andExpect(jsonPath("$[0].user.id", is(1)))
            .andExpect(jsonPath("$[0].user.name", is("John Doe")))
            .andExpect(jsonPath("$[0].movies").doesNotExist())
            .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/movies-lists").param("page", "-1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("Sci-fi Movies")))
            .andExpect(jsonPath("$[0].user.id", is(1)))
            .andExpect(jsonPath("$[0].user.name", is("John Doe")))
            .andExpect(jsonPath("$[0].movies").doesNotExist())
            .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/movies-lists").param("page", "1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(1)))
            .andExpect(jsonPath("$[0].title", is("Sci-fi Movies")))
            .andExpect(jsonPath("$[0].user.id", is(1)))
            .andExpect(jsonPath("$[0].user.name", is("John Doe")))
            .andExpect(jsonPath("$[0].movies").doesNotExist())
            .andExpect(jsonPath("$", hasSize(2)));

        // then
        verify(moviesListService, times(3)).getMoviesLists(anyInt());
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
            .andExpect(jsonPath("$.movies", hasSize(1)));
        // then
        verify(moviesListService, times(1)).findById(anyLong());    }
}
