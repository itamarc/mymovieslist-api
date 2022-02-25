package io.itamarc.mymovieslistapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import io.itamarc.mymovieslistapi.repositories.UserRepository;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

public class UserServiceImplTest {
    private UserService userService;

    @Mock
    UserRepository userRepository;

    AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl(userRepository);
    }
    
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void findById() {
        String name = "Test User One";
        // given
        User user = new User();
        user.setId(1L);
        user.setName(name);

        MoviesList moviesList = new MoviesList();
        moviesList.setId(1L);
        moviesList.setTitle("Test Movies List One");
        user.getMoviesLists().add(moviesList);

        // when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        UserPayload foundUser = userService.findById(1L);

        // then
        assertEquals(1L, foundUser.getId());
        assertEquals(name, foundUser.getName());
        assertEquals(1, foundUser.getMoviesLists().size());
        verify(userRepository, times(1)).findById(anyLong());
    }
}
