package io.itamarc.mymovieslistapi.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.itamarc.mymovieslistapi.model.AuthProvider;

public class MoviesListPayloadTest {
    UserPayload userPayload;

    @BeforeEach
    void setUp() {
        userPayload = UserPayload.builder()
                        .id(1L)
                        .name("name")
                        .email("email")
                        .password("password")
                        .imageUrl("imageUrl")
                        .registered(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                        .provider(AuthProvider.google)
                        .providerId("providerId")
                        .build();
    }

    @Test
    void canEqualTest() {
        MoviesListPayload moviesListPayload1 = MoviesListPayload.builder()
                .id(1L)
                .title("title")
                .created(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2022, 3, 2, 12, 0, 0))
                .user(userPayload)
                .build();
        moviesListPayload1.canEqual(MoviesListPayload.builder().user(userPayload).build());
    }

    @Test
    void unequalsTest() {
        MoviesListPayload moviesListPayload1 = MoviesListPayload.builder()
                .id(1L)
                .title("title")
                .created(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2022, 3, 2, 12, 0, 0))
                .user(userPayload)
                .build();
        MoviesListPayload moviesListPayload2 = MoviesListPayload.builder()
                .id(2L)
                .title("title")
                .created(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2022, 3, 2, 12, 0, 0))
                .user(userPayload)
                .build();
        MoviesListPayload moviesListPayload3 = MoviesListPayload.builder()
                .id(1L)
                .title("other title")
                .created(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2022, 3, 2, 12, 0, 0))
                .user(userPayload)
                .build();

        assertNotEquals(moviesListPayload1, moviesListPayload2);
        assertNotEquals(moviesListPayload1, moviesListPayload3);
        assertNotEquals(moviesListPayload1, null);
        assertNotEquals(moviesListPayload1, new Object());
    }

    @Test
    void equalsTest() {
        MoviesListPayload moviesListPayload1 = MoviesListPayload.builder()
                .title("title")
                .created(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2022, 3, 2, 12, 0, 0))
                .user(userPayload)
                .build();
        MoviesListPayload moviesListPayload2 = MoviesListPayload.builder()
                .title("title")
                .created(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2022, 3, 2, 12, 0, 0))
                .user(userPayload)
                .build();

        assertEquals(moviesListPayload1, moviesListPayload2);
    }

    @Test
    void gettersAndSetters() {
        MoviesListPayload moviesListPayload = new MoviesListPayload();
        moviesListPayload.setId(1L);
        moviesListPayload.setTitle("title");
        moviesListPayload.setCreated(LocalDateTime.of(2022, 3, 1, 12, 0, 0));
        moviesListPayload.setUpdated(LocalDateTime.of(2022, 3, 2, 12, 0, 0));
        moviesListPayload.setUser(userPayload);
        moviesListPayload.setMovies(new HashSet<>());

        assertEquals(1L, moviesListPayload.getId());
        assertEquals("title", moviesListPayload.getTitle());
        assertEquals(LocalDateTime.of(2022, 3, 1, 12, 0, 0), moviesListPayload.getCreated());
        assertEquals(LocalDateTime.of(2022, 3, 2, 12, 0, 0), moviesListPayload.getUpdated());
        assertEquals(userPayload, moviesListPayload.getUser());
        assertEquals(0, moviesListPayload.getMovies().size());
    }

    @Test
    void hashCodeTest() {
        MoviesListPayload moviesListPayload1 = MoviesListPayload.builder()
                .id(1L)
                .title("title")
                .created(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2022, 3, 2, 12, 0, 0))
                .user(userPayload)
                .build();
        MoviesListPayload moviesListPayload2 = MoviesListPayload.builder()
                .id(1L)
                .title("title")
                .created(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2022, 3, 2, 12, 0, 0))
                .user(userPayload)
                .build();

        assertEquals(moviesListPayload1.hashCode(), moviesListPayload2.hashCode());
    }

    @Test
    void testToString() {
        MoviesListPayload moviesListPayload = MoviesListPayload.builder()
                .id(1L)
                .title("title")
                .created(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .updated(LocalDateTime.of(2022, 3, 2, 12, 0, 0))
                .user(userPayload)
                .build();
        assertEquals("MoviesListPayload [id=1, title=title, created=2022-03-01T12:00, updated=2022-03-02T12:00, user.id=1, moviesCount=0, movies=[]]",
                moviesListPayload.toString());
    }
}
