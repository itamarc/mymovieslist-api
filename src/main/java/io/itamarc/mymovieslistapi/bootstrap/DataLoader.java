package io.itamarc.mymovieslistapi.bootstrap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // TODO Check if the database is empty
        loadData();
    }

    private void loadData() {
        // TODO Load data for testing
        User user1 = new User();
        user1.setName("Itamar");
        user1.setEmail("itamarc@gmail.com");
        user1.setPassword("123456");
        user1.setAvatarUrl("https://avatars2.githubusercontent.com/u/29181491?s=460&v=4");
        userRepository.save(user1);

        User user2 = new User();
        user2.setName("John");
        user2.setEmail("john.constantine@realhell.com");
        user2.setPassword("123456");
        user2.setAvatarUrl("https://avatars2.githubusercontent.com/u/29181491?s=460&v=4");
        userRepository.save(user2);
    }
}
