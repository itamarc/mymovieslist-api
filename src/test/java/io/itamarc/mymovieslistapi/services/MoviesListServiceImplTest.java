package io.itamarc.mymovieslistapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.itamarc.mymovieslistapi.model.Movie;
import io.itamarc.mymovieslistapi.model.MovieRank;
import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.MoviesListRepository;
import io.itamarc.mymovieslistapi.transfer.MoviePayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;

public class MoviesListServiceImplTest {
    private MoviesListService moviesListService;

    @Mock
    MoviesListRepository moviesListRepository;

    AutoCloseable closeable;

    MoviesList moviesList;
    private final String TITLE = "Some Movies List";

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        moviesListService = new MoviesListServiceImpl(moviesListRepository);

        moviesList = new MoviesList();
        moviesList.setId(1L);
        moviesList.setTitle(TITLE);
        moviesList.setUpdated(LocalDate.of(2022, 03, 02));
        User user = new User();
        user.setId(2L);
        user.setName("Itamar");
        user.setEmail("itamarc@gmail.com");
        moviesList.setUser(user);
        Movie movie = new Movie();
        movie.setId(4L);
        movie.setTitle("Some Movie");
        movie.setDescription("some description");
        movie.setYear(2010);
        movie.setImageUrl("/notfound.jpg");
        MovieRank movieRank = new MovieRank();
        movieRank.setId(5L);
        movieRank.setRank(7);
        movieRank.setWatched(false);
        movieRank.setMovie(movie);
        movieRank.setUser(user);
        moviesList.addMovieRank(movieRank);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findMovieListById() {
        // given data in setUp method

        // when
        when(moviesListRepository.findById(anyLong())).thenReturn(Optional.of(moviesList));
        MoviesListPayload foundList = moviesListService.findById(1L);

        // then
        assertNotNull(foundList);
        assertEquals(1L, foundList.getId());
        assertEquals(TITLE, foundList.getTitle());
        assertEquals(2L, foundList.getUser().getId());
        assertEquals(1, foundList.getMovies().size());
        assertInstanceOf(MoviePayload.class, foundList.getMovies().iterator().next());
        verify(moviesListRepository, times(1)).findById(anyLong());
    }

    @Test
    void findMovieListByIdNotFound() {
        // given no data

        // when
        when(moviesListRepository.findById(anyLong())).thenReturn(Optional.empty());
        MoviesListPayload foundList = moviesListService.findById(1L);

        // then
        assertNull(foundList);
        verify(moviesListRepository, times(1)).findById(anyLong());
    }

    @Test
    void getMoviesLists() {
        // given data in setUp method and:
        MoviesList moviesList2 = new MoviesList();
        moviesList2.setId(2L);
        moviesList2.setTitle("Some Movies List 2");
        moviesList2.setUpdated(LocalDate.of(2022, 03, 02));
        User user = new User();
        user.setId(3L);
        user.setName("John Doe");
        user.setEmail("johndoe@someweirdemail.cc");
        moviesList2.setUser(user);

        // when
        when(moviesListRepository.findAll()).thenReturn(List.of(moviesList, moviesList2));
        moviesListService.getMoviesLists(-1);
        Set<MoviesListPayload> foundLists = moviesListService.getMoviesLists(1);

        // then
        MoviesListPayload firstListFound = foundLists.iterator().next();
        assertNotNull(foundLists);
        assertEquals(2, foundLists.size());
        assertEquals(TITLE, firstListFound.getTitle());
        assertEquals(2L, firstListFound.getUser().getId());
        assertEquals(1, firstListFound.getMovies().size());
        verify(moviesListRepository, times(2)).findAll();
    }
}
