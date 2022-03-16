package io.itamarc.mymovieslistapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

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
    void findAll() {
        // given
        String name = "Test User One";
        User user = new User();
        user.setId(1L);
        user.setName(name);

        String name2 = "Test User Two";
        User user2 = new User();
        user2.setId(2L);
        user2.setName(name2);

        Set<User> users = new LinkedHashSet<>(Arrays.asList(user, user2));

        when(userRepository.findAll()).thenReturn(users);

        // when
        Set<UserPayload> result = userService.findAll();

        // then
        assertEquals(2, result.size());
        assertEquals(user.getId(), result.stream().findFirst().get().getId());
        assertEquals(user.getName(), result.stream().findFirst().get().getName());
        assertEquals(user2.getId(), result.stream().skip(1).findFirst().get().getId());
        assertEquals(user2.getName(), result.stream().skip(1).findFirst().get().getName());
    }

    @Test
    void findById() {
        // given
        String name = "Test User One";
        User user = new User();
        user.setId(1L);
        user.setName(name);

        MoviesList moviesList = new MoviesList();
        moviesList.setId(1L);
        moviesList.setTitle("Test Movies List One");
        user.getMoviesLists().add(moviesList);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        // when
        UserPayload foundUser = userService.findById(1L);

        // then
        assertEquals(1L, foundUser.getId());
        assertEquals(name, foundUser.getName());
        assertEquals(1, foundUser.getMoviesLists().size());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void findByIdNonExistent() {
        // given no user with id
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        // when
        UserPayload foundUser = userService.findById(1L);

        // then
        assertNull(foundUser);
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    void existsByEmail() {
        // given
        String email = "johndoe@weirdemailserver.cc";

        when(userRepository.existsByEmail(anyString())).thenReturn(true);

        // when
        boolean found = userService.existsByEmail(email);

        // then
        assertTrue(found);
        verify(userRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    void existsByEmailNotFound() {
        // given
        String email = "johndoe@weirdemailserver.cc";

        when(userRepository.existsByEmail(anyString())).thenReturn(false);

        // when
        boolean found = userService.existsByEmail(email);

        // then
        assertFalse(found);
        verify(userRepository, times(1)).existsByEmail(anyString());
    }

    @Test
    void save() {
        // given
        Long id = 1L;
        String name = "John Doe";
        String email = "johndoe@weirdemailserver.cc";
        String password = "123456";
        String imageUrl = "/notfound.png";
        LocalDateTime registered = LocalDateTime.of(2022, 3, 3, 12, 0, 0);

        UserPayload userPayload = UserPayload.builder()
                .id(1L)
                .name(name)
                .email(email)
                .password(password)
                .imageUrl(imageUrl)
                .registered(registered)
                .build();

        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setImageUrl(imageUrl);
        user.setRegistered(registered);

        when(userRepository.save(any(User.class))).thenReturn(user);

        // when
        UserPayload savedUser = userService.save(userPayload);

        // then
        assertEquals(id, savedUser.getId());
        assertEquals(name, savedUser.getName());
        assertEquals(email, savedUser.getEmail());
        assertEquals(password, savedUser.getPassword());
        assertEquals(imageUrl, savedUser.getImageUrl());
        assertEquals(registered, savedUser.getRegistered());
    }
}
