package io.itamarc.mymovieslistapi.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MoviePayloadTest {
    @Test
    void canEqualTest() {
        MoviePayload moviePayload1 = MoviePayload.builder()
                .id(1L)
                .title("title")
                .description("description")
                .year(2000)
                .imageUrl("/image.jpg")
                .rank(9)
                .watched(true)
                .build();
        assertTrue(moviePayload1.canEqual(new MoviePayload()));
        assertFalse(moviePayload1.canEqual(null));
        assertFalse(moviePayload1.canEqual(new Object()));
    }

    @Test
    void unequalsTest() {
        MoviePayload moviePayload1 = MoviePayload.builder()
                .id(1L)
                .title("title")
                .description("description")
                .year(2000)
                .imageUrl("/image.jpg")
                .rank(9)
                .watched(true)
                .build();
        MoviePayload moviePayload2 = MoviePayload.builder()
                .id(2L)
                .title("title")
                .description("description")
                .year(2000)
                .imageUrl("/image.jpg")
                .rank(9)
                .watched(true)
                .build();
        MoviePayload moviePayload3 = MoviePayload.builder()
                .id(1L)
                .title("other title")
                .description("other description")
                .year(2000)
                .imageUrl("/image.jpg")
                .rank(9)
                .watched(true)
                .build();

        assertFalse(moviePayload1.equals(moviePayload2));
        assertFalse(moviePayload1.equals(moviePayload3));
        assertFalse(moviePayload1.equals(null));
        assertFalse(moviePayload1.equals(new Object()));
    }

    @Test
    void equalsTest() {
        MoviePayload moviePayload1 = MoviePayload.builder()
                .title("title")
                .description("description")
                .year(2000)
                .imageUrl("/image.jpg")
                .rank(9)
                .watched(true)
                .build();
        MoviePayload moviePayload2 = MoviePayload.builder()
                .title("title")
                .description("description")
                .year(2000)
                .imageUrl("/image.jpg")
                .rank(9)
                .watched(true)
                .build();

        assertTrue(moviePayload1.equals(moviePayload2));
    }

    @Test
    void gettersAndSetters() {
        MoviePayload moviePayload = new MoviePayload();
        moviePayload.setId(1L);
        moviePayload.setTitle("title");
        moviePayload.setDescription("description");
        moviePayload.setYear(2000);
        moviePayload.setImageUrl("/image.jpg");
        moviePayload.setRank(9);
        moviePayload.setWatched(true);

        assertEquals(1L, moviePayload.getId());
        assertEquals("title", moviePayload.getTitle());
        assertEquals("description", moviePayload.getDescription());
        assertEquals(2000, moviePayload.getYear());
        assertEquals("/image.jpg", moviePayload.getImageUrl());
        assertEquals(9, moviePayload.getRank());
        assertTrue(moviePayload.isWatched());
    }

    @Test
    void hashCodeTest() {
        MoviePayload moviePayload1 = MoviePayload.builder()
                .id(1L)
                .title("title")
                .description("description")
                .year(2000)
                .imageUrl("/image.jpg")
                .rank(9)
                .watched(true)
                .build();
        MoviePayload moviePayload2 = MoviePayload.builder()
                .id(1L)
                .title("title")
                .description("description")
                .year(2000)
                .imageUrl("/image.jpg")
                .rank(9)
                .watched(true)
                .build();

        assertEquals(moviePayload1.hashCode(), moviePayload2.hashCode());
    }
}
