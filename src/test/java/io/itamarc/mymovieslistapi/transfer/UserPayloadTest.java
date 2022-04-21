package io.itamarc.mymovieslistapi.transfer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import io.itamarc.mymovieslistapi.model.AuthProvider;

public class UserPayloadTest {

    @Test
    void unequals() {
        LocalDateTime dateTime = LocalDateTime.now();
        UserPayload userPayload1 = UserPayload.builder()
                .id(1L)
                .name("name")
                .email("email")
                .password("password")
                .imageUrl("imageUrl")
                .registered(dateTime)
                .provider(AuthProvider.google)
                .providerId("providerId")
                .build();
        UserPayload userPayload2 = UserPayload.builder()
                .id(2L)
                .name("name")
                .email("email")
                .password("password")
                .imageUrl("imageUrl")
                .registered(dateTime)
                .provider(AuthProvider.google)
                .providerId("providerId")
                .build();
        UserPayload userPayload3 = UserPayload.builder()
                .id(1L)
                .name("name")
                .email("other email")
                .password("password")
                .imageUrl("imageUrl")
                .registered(dateTime)
                .provider(AuthProvider.google)
                .providerId("providerId")
                .build();

        assertNotEquals(userPayload1, userPayload2);
        assertNotEquals(userPayload1, userPayload3);
        assertNotEquals(userPayload1, null);
        assertNotEquals(userPayload1, new Object());
    }

    @Test
    void equalsTest() {
        LocalDateTime dateTime = LocalDateTime.now();
        UserPayload userPayload1 = UserPayload.builder()
                .name("name")
                .email("email")
                .password("password")
                .imageUrl("imageUrl")
                .registered(dateTime)
                .provider(AuthProvider.google)
                .providerId("providerId")
                .build();
        UserPayload userPayload2 = UserPayload.builder()
                .name("name")
                .email("email")
                .password("password")
                .imageUrl("imageUrl")
                .registered(dateTime)
                .provider(AuthProvider.google)
                .providerId("providerId")
                .build();

        assertEquals(userPayload1, userPayload2);
    }

    @Test
    void hashCodeTest() {
        LocalDateTime dateTime = LocalDateTime.now();
        UserPayload userPayload1 = UserPayload.builder()
                .name("name")
                .email("email")
                .password("password")
                .imageUrl("imageUrl")
                .registered(dateTime)
                .provider(AuthProvider.google)
                .providerId("providerId")
                .build();
        UserPayload userPayload2 = UserPayload.builder()
                .name("name")
                .email("email")
                .password("password")
                .imageUrl("imageUrl")
                .registered(dateTime)
                .provider(AuthProvider.google)
                .providerId("providerId")
                .build();

        assertEquals(userPayload1.hashCode(), userPayload2.hashCode());
    }

    @Test
    void toStringTest() {
        UserPayload userPayload = UserPayload.builder()
                .name("name")
                .email("email")
                .password("password")
                .imageUrl("imageUrl")
                .registered(LocalDateTime.of(2022, 3, 1, 12, 0, 0))
                .provider(AuthProvider.google)
                .providerId("providerId")
                .emailVerified(true)
                .build();

        assertEquals("UserPayload(id=null, name=name, email=email, password=password, imageUrl=imageUrl, emailVerified=true, registered=2022-03-01T12:00, provider=google, providerId=providerId, moviesLists=[])", userPayload.toString());
    }

    @Test
    void gettersAndSetters() {
        UserPayload userPayload = UserPayload.builder().build();
        userPayload.setId(1L);
        userPayload.setName("name");
        userPayload.setEmail("email");
        userPayload.setPassword("password");
        userPayload.setImageUrl("imageUrl");
        userPayload.setRegistered(LocalDateTime.of(2022, 3, 1, 12, 0, 0));
        userPayload.setProvider(AuthProvider.google);
        userPayload.setProviderId("providerId");

        assertEquals(1L, userPayload.getId());
        assertEquals("name", userPayload.getName());
        assertEquals("email", userPayload.getEmail());
        assertEquals("password", userPayload.getPassword());
        assertEquals("imageUrl", userPayload.getImageUrl());
        assertEquals(LocalDateTime.of(2022, 3, 1, 12, 0, 0), userPayload.getRegistered());
        assertEquals(AuthProvider.google, userPayload.getProvider());
        assertEquals("providerId", userPayload.getProviderId());
    }
}
