package io.itamarc.mymovieslistapi.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.UserRepository;

public class CustomUserDetailsServiceTest {
    CustomUserDetailsService service;

    @Mock
    UserRepository userRepository;

    AutoCloseable closeable;

    private final String NAME = "John Doe";
    private final String EMAIL = "jonhdoe@someweirdemail.cc";
    private final String PASSWORD = "password";
    User user;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        service = new CustomUserDetailsService(userRepository);

        user = new User();
        user.setId(1L);
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setPassword(PASSWORD);

        MoviesList moviesList = new MoviesList();
        moviesList.setId(1L);
        moviesList.setTitle("Test Movies List One");
        user.getMoviesLists().add(moviesList);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void testLoadUserById() {
        // given data in setUp method

        // when
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        UserDetails foundUser = service.loadUserById(1L);

        // then
        assertEquals(EMAIL, foundUser.getUsername());
        assertEquals(PASSWORD, foundUser.getPassword());
    }

    @Test
    void testLoadUserByUsername() {
        // given data in setUp method

        // when
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        UserDetails foundUser = service.loadUserByUsername(EMAIL);

        // then
        assertEquals(EMAIL, foundUser.getUsername());
        assertEquals(PASSWORD, foundUser.getPassword());
    }
}
