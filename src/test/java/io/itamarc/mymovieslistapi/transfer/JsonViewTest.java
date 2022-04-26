package io.itamarc.mymovieslistapi.transfer;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonViewTest {

    ObjectMapper mapper;

    UserPayload user;
    MoviePayload movie1;
    MoviesListPayload moviesListPayload;


    @BeforeEach
    void setUp() {
        user = UserPayload.builder().id(1L).name("John Doe").email("johndoe@someweirdemail.cc").build();
        movie1 = MoviePayload.builder()
            .id(1L)
            .title("Movie 1")
            .description("Some movie 1")
            .imageUrl("/notfound.jpg")
            .rank(5)
            .watched(true)
            .build();
        moviesListPayload = MoviesListPayload.builder()
                .id(1L)
                .title("Sci-fi Movies")
                .user(user)
                .build();
        moviesListPayload.getMovies().add(movie1);
        mapper = JsonMapper.builder().disable(MapperFeature.DEFAULT_VIEW_INCLUSION).build();
    }

    @Test
    public void getMoviesListByIdJson() throws JsonProcessingException {
        String result = mapper.writerWithView(MoviesListViews.MoviesListWithMovies.class)
            .writeValueAsString(moviesListPayload);

        assertTrue(result.contains("Sci-fi Movies"));
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("Movie 1"));

        assertFalse(result.contains("password"));
    }

    @Test
    public void getMoviesListWithMoviesByIdJson() throws JsonProcessingException {
        String result = mapper.writerWithView(MoviesListViews.MoviesListBasic.class)
            .writeValueAsString(moviesListPayload);

        assertTrue(result.contains("Sci-fi Movies"));
        assertTrue(result.contains("John Doe"));

        assertFalse(result.contains("\"password\""));
        assertFalse(result.contains("\"movies\""));
    }
}
