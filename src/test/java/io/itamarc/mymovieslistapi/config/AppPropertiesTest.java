package io.itamarc.mymovieslistapi.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = AppProperties.class)
@TestPropertySource("classpath:application-test.properties")
public class AppPropertiesTest {
    @Autowired
    AppProperties appProperties;

    @Test
    void testGetAuth() {
        assertEquals("someTokenSecretLongEnoughToBeUsedAsAKey", appProperties.getAuth().getTokenSecret());
    }
}
