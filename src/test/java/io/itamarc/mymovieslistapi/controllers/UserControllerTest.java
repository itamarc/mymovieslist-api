package io.itamarc.mymovieslistapi.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io.itamarc.mymovieslistapi.model.AuthProvider;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.security.UserPrincipal;
import io.itamarc.mymovieslistapi.services.UserService;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

public class UserControllerTest {
    @Mock
    private UserService service;

    private UserController controller;

    MockMvc mockMvc;

    AutoCloseable closeable;

    private final Long ID = 15L;
    private final String NAME = "John Doe";
    private final String EMAIL = "johndoe@weirdemailserver.cc";
    private final String IMAGE_URL = "/user/avatar.jpg";
    private final LocalDate REGISTERED = LocalDate.of(2022, 3, 3);

    UserPayload user;
    Set<UserPayload> users;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        controller = new UserController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        user = UserPayload.builder()
                .id(ID)
                .name(NAME)
                .email(EMAIL)
                .imageUrl(IMAGE_URL)
                .registered(REGISTERED)
                .build();
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
        mockMvc.perform(get("/users"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id", is(15)))
            .andExpect(jsonPath("$[0].name", is(NAME)))
            .andExpect(jsonPath("$[0].email", is(EMAIL)))
            .andExpect(jsonPath("$[0].imageUrl", is(IMAGE_URL)))
            .andExpect(jsonPath("$", hasSize(2)));

        // then
        verify(service, times(1)).findAll();
    }

    @Test
    void getCurrentUser() throws Exception {
        // given
        when(service.findById(any())).thenReturn(user);

        // when
        mockMvc.perform(get("/user/me"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(15)))
            .andExpect(jsonPath("$.name", is(NAME)))
            .andExpect(jsonPath("$.email", is(EMAIL)))
            .andExpect(jsonPath("$.imageUrl", is(IMAGE_URL)))
            .andExpect(jsonPath("$.moviesLists").doesNotExist());

        // then
        verify(service, times(1)).findById(any());
    }

    @Test
    void getUserById() throws Exception {
        // given
        when(service.findById(anyLong())).thenReturn(user);

        // when
        mockMvc.perform(get("/users/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id", is(15)))
            .andExpect(jsonPath("$.name", is(NAME)))
            .andExpect(jsonPath("$.email", is(EMAIL)))
            .andExpect(jsonPath("$.imageUrl", is(IMAGE_URL)))
            .andExpect(jsonPath("$.moviesLists").exists());

        // then
        verify(service, times(1)).findById(anyLong());
    }
}
