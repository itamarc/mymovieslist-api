package io.itamarc.mymovieslistapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(properties = {"spring.profiles.active=test"})
@ActiveProfiles("test")
class MymovieslistApiApplicationTests {

	@Test
	void contextLoads() {
	}
}
