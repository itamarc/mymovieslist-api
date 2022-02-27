package io.itamarc.mymovieslistapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.MoviesListRepository;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;

public class MoviesListServiceImplTest {
    private MoviesListService moviesListService;

    @Mock
    MoviesListRepository moviesListRepository;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        moviesListService = new MoviesListServiceImpl(moviesListRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findMovieListById() {
        // given
        String title = "Some Movies List";
        MoviesList moviesList = new MoviesList();
        moviesList.setId(1L);
        moviesList.setTitle(title);
        User user = new User();
        user.setId(2L);
        user.setName("Itamar");
        user.setEmail("itamarc@gmail.com");
        moviesList.setUser(user);

        // when
        when(moviesListRepository.findById(anyLong())).thenReturn(Optional.of(moviesList));
        MoviesListPayload foundList = moviesListService.findById(1L);

        // then
        assertNotNull(foundList);
        assertEquals(1L, foundList.getId());
        assertEquals(title, foundList.getTitle());
        assertEquals(2L, foundList.getUser().getId());
        verify(moviesListRepository, times(1)).findById(anyLong());
    }
}
