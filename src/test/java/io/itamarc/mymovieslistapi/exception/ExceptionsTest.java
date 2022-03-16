package io.itamarc.mymovieslistapi.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class ExceptionsTest {
    @Test
    void badRequestExceptionConstructors() {
        BadRequestException e1 = new BadRequestException("message1");
        BadRequestException e2 = new BadRequestException("message2", new RuntimeException());

        assertEquals("message1", e1.getMessage());
        assertEquals("message2", e2.getMessage());
        assertEquals(RuntimeException.class, e2.getCause().getClass());
    }

    @Test
    void oAuth2AuthenticationProcessingExceptionConstructors() {
        OAuth2AuthenticationProcessingException e3 = new OAuth2AuthenticationProcessingException("message3");
        OAuth2AuthenticationProcessingException e4 = new OAuth2AuthenticationProcessingException("message4", new RuntimeException());

        assertEquals("message3", e3.getMessage());
        assertEquals("message4", e4.getMessage());
        assertEquals(RuntimeException.class, e4.getCause().getClass());
    }
}
