package io.itamarc.mymovieslistapi.bootstrap;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import io.itamarc.mymovieslistapi.model.AuthProvider;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Profile({ "dev", "prod" })
public class DataLoaderAdminUser implements CommandLineRunner {
    UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Long ADMIN_ID = 0L;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    public DataLoaderAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsById(ADMIN_ID)) {
            log.debug("Loading Admin User data - active profile: '" + activeProfile + "'.");
            User admin = new User();
            admin.setId(ADMIN_ID);
            admin.setName("Administrator");
            admin.setEmail("admin@appho.me");
            admin.setPassword(passwordEncoder.encode("changethispwd"));
            admin.setImageUrl("https://pickaface.net/gallery/avatar/manthan99953f1b33bb20ba.png");
            admin.setRegistered(LocalDateTime.now());
            admin.setProvider(AuthProvider.local);
            admin.setEmailVerified(true);
            User savedAdminUser = userRepository.save(admin);
            log.info("Saved user: " + savedAdminUser.getName());
                log.debug("Data fully loaded in bootstrap.");
        }
    }
}
