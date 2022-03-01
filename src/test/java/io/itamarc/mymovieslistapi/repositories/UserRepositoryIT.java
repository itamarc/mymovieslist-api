package io.itamarc.mymovieslistapi.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.itamarc.mymovieslistapi.model.AuthProvider;
import io.itamarc.mymovieslistapi.model.User;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class UserRepositoryIT {
    @Autowired
    UserRepository userRepository;

    static User user;
    static final Long USER_ID = 1L;
    static final String NAME = "Itamar Carvalho";
    static final String EMAIL = "itamarc@gmail.com";
    static final LocalDate REGISTERED = LocalDate.now();

    @BeforeAll
    static void beforeClass() {
        user = new User();
        user.setId(USER_ID);
        user.setName(NAME);
        user.setEmail(EMAIL);
        user.setEmailVerified(true);
        user.setRegistered(REGISTERED);
        user.setProvider(AuthProvider.local);
    }
    @BeforeEach
    void setUp() {
        userRepository.save(user);
    }

    @Test
    void findById() {
        Optional<User> optionalUser = userRepository.findById(USER_ID);
        User foundUser = optionalUser.get();
        assertTrue(optionalUser.isPresent());
        assertEquals(NAME, foundUser.getName());
        assertEquals(REGISTERED, foundUser.getRegistered());
    }
}
