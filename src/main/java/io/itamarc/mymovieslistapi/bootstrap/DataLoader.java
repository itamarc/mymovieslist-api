package io.itamarc.mymovieslistapi.bootstrap;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.MoviesListRepository;
import io.itamarc.mymovieslistapi.repositories.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    UserRepository userRepository;
    MoviesListRepository moviesListRepository;

    public DataLoader(UserRepository userRepository, MoviesListRepository moviesListRepository) {
        this.userRepository = userRepository;
        this.moviesListRepository = moviesListRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            loadData();
        }
    }

    private void loadData() {
        // TODO Load data for testing
        User user1 = new User();
        user1.setName("Itamar");
        user1.setEmail("itamarc@gmail.com");
        user1.setPassword("123456");
        user1.setAvatarUrl("https://avatars.githubusercontent.com/u/19577272?v=4");
        user1.setRegistered(LocalDate.now());
        User savedUser1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("John");
        user2.setEmail("john.constantine@realhell.com");
        user2.setPassword("123456");
        user2.setAvatarUrl("https://www.superherodb.com/pictures2/portraits/10/100/718.jpg");
        user2.setRegistered(LocalDate.now());
        User savedUser2 = userRepository.save(user2);

        MoviesList moviesList1 = new MoviesList();
        moviesList1.setTitle("Sci-fi Movies");
        moviesList1.setUser(savedUser1);
        moviesList1.setCreated(LocalDate.now());
        moviesList1.setUpdated(LocalDate.now());
        MoviesList savedMoviesList1 = moviesListRepository.save(moviesList1);

        MoviesList moviesList2 = new MoviesList();
        moviesList2.setTitle("Comedy Movies");
        moviesList2.setUser(savedUser2);
        moviesList2.setCreated(LocalDate.now());
        moviesList2.setUpdated(LocalDate.now());
        MoviesList savedMoviesList2 = moviesListRepository.save(moviesList2);
    }
}
