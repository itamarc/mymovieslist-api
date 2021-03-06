package io.itamarc.mymovieslistapi.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.itamarc.mymovieslistapi.model.Movie;

@ExtendWith(SpringExtension.class)
@SpringBootTest(properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
public class MovieRepositoryIT {
    @Autowired
    MovieRepository movieRepository;

    @Test
    void findByTitle() {
        Optional<Movie> movieOptional = movieRepository.findByTitle("Death on the Nile");

        assertEquals("Death on the Nile", movieOptional.get().getTitle());
    }
}
