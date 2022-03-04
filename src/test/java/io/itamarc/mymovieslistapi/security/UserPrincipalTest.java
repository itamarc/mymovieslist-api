package io.itamarc.mymovieslistapi.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.itamarc.mymovieslistapi.model.User;

public class UserPrincipalTest {
    User user;
    UserPrincipal userPrincipal;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("johndoe@weirdemailaddress.cc");
        user.setPassword("password");
    }

    @Test
    void createWithMap() {
        userPrincipal = UserPrincipal.create(user, Collections.emptyMap());
        assertEquals(user.getId(), userPrincipal.getId());
        assertEquals(String.valueOf(user.getId()), userPrincipal.getName());
        assertEquals(user.getEmail(), userPrincipal.getEmail());
        assertEquals(user.getEmail(), userPrincipal.getUsername());
        assertEquals(user.getPassword(), userPrincipal.getPassword());
        assertEquals(Collections.emptyMap(), userPrincipal.getAttributes());
    }

    @Test
    void attributes() {
        userPrincipal = UserPrincipal.create(user, Collections.emptyMap());
        assertTrue(userPrincipal.isAccountNonExpired());
        assertTrue(userPrincipal.isAccountNonLocked());
        assertTrue(userPrincipal.isCredentialsNonExpired());
        assertTrue(userPrincipal.isEnabled());
        assertEquals(1, userPrincipal.getAuthorities().size());
    }
}
