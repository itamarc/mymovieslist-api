package io.itamarc.mymovieslistapi.controllers;

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
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import io.itamarc.mymovieslistapi.config.AppProperties;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.security.UserPrincipal;
import io.itamarc.mymovieslistapi.services.UserService;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@EnableConfigurationProperties(value = AppProperties.class)
@TestPropertySource("classpath:application-test.properties")
@SpringBootTest
public class UserControllerIT {
    @Autowired
    private WebApplicationContext context;

    @MockBean
    private UserService service;

    MockMvc mockMvc;

    AutoCloseable closeable;

    private final Long ID = 15L;
    private final String NAME = "John Doe";
    private final String EMAIL = "johndoe@weirdemailserver.cc";
    private final String IMAGE_URL = "/user/avatar.jpg";
    private final LocalDate REGISTERED = LocalDate.of(2022, 3, 3);

    UserPayload user;
    Set<UserPayload> users;
    UserPrincipal principal;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
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
    @WithMockUser
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
    @WithMockUser
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
