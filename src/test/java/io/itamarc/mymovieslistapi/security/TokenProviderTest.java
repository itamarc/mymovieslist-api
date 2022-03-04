package io.itamarc.mymovieslistapi.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.itamarc.mymovieslistapi.config.AppProperties;
import io.itamarc.mymovieslistapi.model.User;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@EnableConfigurationProperties(value = AppProperties.class)
@TestPropertySource("classpath:application-test.properties")
public class TokenProviderTest {
    TokenProvider tokenProvider;

    @Autowired
    AppProperties appProperties;

    @Mock
    Authentication authentication;

    AutoCloseable closeable;

    UserPrincipal principal;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        tokenProvider = new TokenProvider(appProperties);

        User user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("johndoe@weirdemailaddress.cc");
        user.setPassword("password");

        principal = UserPrincipal.create(user);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    @WithMockUser
    void createToken() {
        // given
        when(authentication.getPrincipal()).thenReturn(principal);

        // when
        String token = tokenProvider.createToken(authentication);

        // then
        assertTrue(token.matches("^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+$"));
        assertEquals(125, token.length(), "Token should have 125 characters");
    }

    @Test
    void getUserIdFromToken() {

    }

    @Test
    void validateToken() {

    }
}
