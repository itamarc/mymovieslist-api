package io.itamarc.mymovieslistapi.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.Charset;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import io.itamarc.mymovieslistapi.model.AuthProvider;
import io.itamarc.mymovieslistapi.security.TokenProvider;
import io.itamarc.mymovieslistapi.services.UserService;
import io.itamarc.mymovieslistapi.transfer.LoginRequest;
import io.itamarc.mymovieslistapi.transfer.SignUpRequest;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

public class AuthControllerTest {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
        MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8"));
    private static final String NAME = "John Doe";
    private static final String EMAIL = "johndoe@weirdemailserver.xyz";
    private static final String PASSWORD = "password";

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    TokenProvider tokenProvider;

    AutoCloseable closeable;

    AuthController controller;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        controller = new AuthController(authenticationManager, userService, passwordEncoder, tokenProvider);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void authenticateUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(EMAIL);
        loginRequest.setPassword(PASSWORD);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(loginRequest);

        mockMvc.perform(post("/auth/login").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    void registerUser() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName(NAME);
        signUpRequest.setEmail(EMAIL);
        signUpRequest.setPassword(PASSWORD);

        UserPayload user = UserPayload.builder()
                            .name(NAME)
                            .email(EMAIL)
                            .password(PASSWORD)
                            .provider(AuthProvider.local)
                            .build();

        when(userService.save(any())).thenReturn(user);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(signUpRequest);

        mockMvc.perform(post("/auth/signup").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    void registerUser_EmailInUse() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setName(NAME);
        signUpRequest.setEmail(EMAIL);
        signUpRequest.setPassword(PASSWORD);

        when(userService.existsByEmail(anyString())).thenReturn(true);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(signUpRequest);

        mockMvc.perform(post("/auth/signup").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson))
                .andExpect(status().isBadRequest());
    }
}
