package io.itamarc.mymovieslistapi.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.servlet.http.Cookie;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class CookieUtilsTest {
    MockHttpServletRequest request;
    MockHttpServletResponse response;

    @BeforeEach
    void setUp() {
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
    }

    @Test
    void addCookie() {
        CookieUtils.addCookie(response, "name", "value", 123);
        assertEquals(1, response.getCookies().length);
        assertEquals("name", response.getCookies()[0].getName());
        assertEquals("value", response.getCookies()[0].getValue());
        assertEquals(123, response.getCookies()[0].getMaxAge());
    }

    @Test
    void deleteCookie() {
        // given
        String name = "cookieName";
        Cookie cookie = new Cookie(name, "value");
        request.setCookies(cookie);

        // when
        CookieUtils.deleteCookie(request, response, name);
        CookieUtils.deleteCookie(request, response, "otherName"); // for coverage

        // then
        assertEquals(1, response.getCookies().length);
        assertEquals(name, response.getCookies()[0].getName());
        assertEquals("", response.getCookies()[0].getValue());
        assertEquals(0, response.getCookies()[0].getMaxAge());
    }

    @Test
    void deleteNonExistentCookie() {
        // given no cookies in the request
        request.setCookies((Cookie[])null);

        // when
        CookieUtils.deleteCookie(request, response, "someName");

        // then
        assertEquals(0, response.getCookies().length);
    }

    @Test
    void deleteCookieEmptyList() {
        // given no cookies in the request
        request.setCookies(new Cookie[0]);

        // when
        CookieUtils.deleteCookie(request, response, "someName");

        // then
        assertEquals(0, response.getCookies().length);
    }

    @Test
    void serialize() {
        String test = "test";
        assertEquals("rO0ABXQABHRlc3Q=", CookieUtils.serialize(test));
    }

    @Test
    void deserialize() {
        String test = "rO0ABXQABHRlc3Q=";
        Cookie cookie = new Cookie("name", test);
        assertEquals("test", CookieUtils.deserialize(cookie, String.class));
    }

    @Test
    void getCookie() {
        // given
        String name = "cookieName";
        request.setCookies(new Cookie(name, "value"));

        // when
        Optional<Cookie> cookieOptional = CookieUtils.getCookie(request, name);
        Optional<Cookie> cookieOptional2 = CookieUtils.getCookie(request, "otherName");

        // then
        assertTrue(cookieOptional.isPresent());
        assertEquals(name, cookieOptional.get().getName());
        assertEquals("value", cookieOptional.get().getValue());

        assertTrue(cookieOptional2.isEmpty());
    }

    @Test
    void getCookieNoCookies() {
        // given no cookies in the request
        String name = "cookieName";
        request.setCookies((Cookie[])null);

        // when
        Optional<Cookie> cookieOptional = CookieUtils.getCookie(request, name);

        // then
        assertTrue(cookieOptional.isEmpty());
    }

    @Test
    void getCookieEmptyList() {
        // given no cookies in the request
        String name = "cookieName";
        request.setCookies(new Cookie[0]);

        // when
        Optional<Cookie> cookieOptional = CookieUtils.getCookie(request, name);

        // then
        assertTrue(cookieOptional.isEmpty());
    }
}
