package io.itamarc.mymovieslistapi.security.oauth2.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;

import org.junit.jupiter.api.Test;

import io.itamarc.mymovieslistapi.exception.OAuth2AuthenticationProcessingException;

public class OAuth2UserInfoFactoryTest {
    @Test
    void getOAuth2UserInfo() {
        Map<String, Object> map = Map.of(
            "sub", "1234567890",
            "name", "John Doe",
            "email", "johndoe@someweirdemail.xyz",
            "picture", "/picture.jpg");

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo("google", map);
        assertEquals("1234567890", userInfo.getId());
        assertEquals("John Doe", userInfo.getName());
        assertEquals("johndoe@someweirdemail.xyz", userInfo.getEmail());
        assertEquals("/picture.jpg", userInfo.getImageUrl());
    }

    @Test
    void getOAuth2UserInfoInvalid() {
        assertThrows(OAuth2AuthenticationProcessingException.class,
                    () -> OAuth2UserInfoFactory.getOAuth2UserInfo("foobar", Map.of()));
    }
}
