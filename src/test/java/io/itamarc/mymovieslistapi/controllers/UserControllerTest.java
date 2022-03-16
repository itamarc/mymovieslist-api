package io.itamarc.mymovieslistapi.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import io.itamarc.mymovieslistapi.exception.ResourceNotFoundException;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.security.UserPrincipal;
import io.itamarc.mymovieslistapi.services.UserService;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

public class UserControllerTest {
    @Mock
    private UserService service;

    private UserController controller;

    AutoCloseable closeable;

    private final Long ID = 15L;
    private final String NAME = "John Doe";
    private final String EMAIL = "johndoe@weirdemailserver.cc";
    private final String IMAGE_URL = "/user/avatar.jpg";
    private final LocalDateTime REGISTERED = LocalDateTime.of(2022, 3, 3, 12, 0, 0);

    UserPayload user;
    Set<UserPayload> users;
    UserPrincipal principal;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        controller = new UserController(service);
        user = UserPayload.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .imageUrl(IMAGE_URL)
                .registered(REGISTERED)
                .build();
        principal = UserPrincipal.create(User.builder().id(ID).email(EMAIL).password("password").build());
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getAllUsers() throws Exception {
        // given
        UserPayload user2 = UserPayload.builder()
                .id(2L)
                .name("Jane Doe")
                .email("janedoe@somemorgue.gov")
                .imageUrl("/user/avatar2.jpg")
                .registered(REGISTERED)
                .build();
        users = new LinkedHashSet<UserPayload>(Arrays.asList(user, user2));

        when(service.findAll()).thenReturn(users);

        // when
        Set<UserPayload> usersFound = controller.getAllUsers();

        // then
        assertEquals(2, usersFound.size());
        assertTrue(usersFound.contains(user));
        assertTrue(usersFound.contains(user2));
        verify(service, times(1)).findAll();
    }

    @Test
    void getCurrentUser() throws Exception {
        // given
        when(service.findById(anyLong())).thenReturn(user);

        // when
        UserPayload userFound = controller.getCurrentUser(principal);

        // then
        assertEquals(user, userFound);
        verify(service, times(1)).findById(any());
    }

    @Test
    void getCurrentUserNotFound() throws Exception {
        // given data in setUp method

        // when
        when(service.findById(any())).thenReturn(null);

        // then
        assertThrows(ResourceNotFoundException.class, () -> controller.getCurrentUser(principal));
        verify(service, times(1)).findById(any());
    }

    @Test
    void getUserById() throws Exception {
        // given
        when(service.findById(anyLong())).thenReturn(user);

        // when
        UserPayload userFound = controller.getUserById(ID);

        // then
        assertEquals(ID, userFound.getId());
        assertEquals(NAME, userFound.getName());
        assertEquals(EMAIL, userFound.getEmail());
        assertEquals(IMAGE_URL, userFound.getImageUrl());
        assertEquals(REGISTERED, userFound.getRegistered());
        verify(service, times(1)).findById(anyLong());
    }
}
