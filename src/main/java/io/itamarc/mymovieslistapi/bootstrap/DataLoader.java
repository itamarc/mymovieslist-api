package io.itamarc.mymovieslistapi.bootstrap;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import io.itamarc.mymovieslistapi.model.Movie;
import io.itamarc.mymovieslistapi.model.MovieRank;
import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.MovieRankRepository;
import io.itamarc.mymovieslistapi.repositories.MovieRepository;
import io.itamarc.mymovieslistapi.repositories.MoviesListRepository;
import io.itamarc.mymovieslistapi.repositories.UserRepository;

@Component
public class DataLoader implements CommandLineRunner {
    UserRepository userRepository;
    MoviesListRepository moviesListRepository;
    MovieRepository movieRepository;
    MovieRankRepository movieRankRepository;

    public DataLoader(UserRepository userRepository, MoviesListRepository moviesListRepository,
            MovieRepository movieRepository, MovieRankRepository movieRankRepository) {
        this.userRepository = userRepository;
        this.moviesListRepository = moviesListRepository;
        this.movieRepository = movieRepository;
        this.movieRankRepository = movieRankRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            loadData();
        }
    }

    private void loadData() {
        User user1 = new User();
        user1.setName("Itamar");
        user1.setEmail("itamarc@gmail.com");
        user1.setPassword("123456");
        user1.setImageUrl("https://avatars.githubusercontent.com/u/19577272?v=4");
        user1.setRegistered(LocalDate.now());
        User savedUser1 = userRepository.save(user1);

        User user2 = new User();
        user2.setName("John");
        user2.setEmail("john.constantine@realhell.com");
        user2.setPassword("123456");
        user2.setImageUrl("https://www.superherodb.com/pictures2/portraits/10/100/718.jpg");
        user2.setRegistered(LocalDate.now());
        User savedUser2 = userRepository.save(user2);

        MoviesList moviesList1 = new MoviesList();
        moviesList1.setTitle("Sci-fi Movies");
        moviesList1.setUser(savedUser1);
        moviesList1.setCreated(LocalDate.now());
        moviesList1.setUpdated(LocalDate.now());
        MoviesList savedMoviesList1 = moviesListRepository.save(moviesList1);

        MoviesList moviesList2 = new MoviesList();
        moviesList2.setTitle("Christmas Movies");
        moviesList2.setUser(savedUser2);
        moviesList2.setCreated(LocalDate.now());
        moviesList2.setUpdated(LocalDate.now());
        MoviesList savedMoviesList2 = moviesListRepository.save(moviesList2);

        Movie movie0 = new Movie();
        movie0.setTitle("The Matrix");
        movie0.setDescription("A computer hacker learns from mysterious rebels about the true nature of his reality and his role in the war against its controllers.");
        movie0.setYear(1999);
        movie0.setImageUrl("/movieimageunavailable.png");
        Movie savedMovie0 = movieRepository.save(movie0);
        
        Movie movie1 = new Movie();
        movie1.setTitle("The Polar Express");
        movie1.setDescription("A christmas story");
        movie1.setYear(2004);
        movie1.setImageUrl("/movieimageunavailable.png");
        Movie savedMovie1 = movieRepository.save(movie1);
        
        Movie movie2 = new Movie();
        movie2.setTitle("The Dark Knight");
        movie2.setDescription("The best Batman movie");
        movie2.setYear(2008);
        movie2.setImageUrl("/movieimageunavailable.png");
        Movie savedMovie2 = movieRepository.save(movie2);
        
        Movie movie3 = new Movie();
        movie3.setTitle("Clash of the Titans");
        movie3.setDescription("Greek mythology action movie");
        movie3.setYear(2010);
        movie3.setImageUrl("/movieimageunavailable.png");
        Movie savedMovie3 = movieRepository.save(movie3);
        
        Movie movie4 = new Movie();
        movie4.setTitle("Inception");
        movie4.setYear(2010);
        movie4.setDescription("A thief, who steals corporate secrets through use of dream-sharing technology, is given the inverse task of planting an idea into the mind of a CEO.");
        movie4.setImageUrl("/movieimageunavailable.png");
        Movie savedMovie4 = movieRepository.save(movie4);
        
        Movie movie5 = new Movie();
        movie5.setTitle("Tenet");
        movie5.setDescription("A CIA agent and a French Connection guy are forced to work together to stop a war");
        movie5.setYear(2020);
        movie5.setImageUrl("/movieimageunavailable.png");
        Movie savedMovie5 = movieRepository.save(movie5);
        
        Movie movie6 = new Movie();
        movie6.setTitle("Interstellar");
        movie6.setDescription("A team of explorers travel through a wormhole in space in an attempt to ensure humanity's survival.");
        movie6.setYear(2014);
        movie6.setImageUrl("/movieimageunavailable.png");
        Movie savedMovie6 = movieRepository.save(movie6);
        
        Movie movie7 = new Movie();
        movie7.setTitle("American Sniper");
        movie7.setDescription("A Navy SEAL is called to the battlefield to join a company of soldiers fighting to defend their nation against an invasion of enemy soldiers.");
        movie7.setYear(2014);
        movie7.setImageUrl("/movieimageunavailable.png");
        Movie savedMovie7 = movieRepository.save(movie7);
        

        MovieRank movieRank1 = new MovieRank();
        movieRank1.setMovie(savedMovie0);
        movieRank1.setRank(10);
        movieRank1.setWatched(true);
        movieRank1.setUser(savedUser1);
        movieRank1.addMoviesList(savedMoviesList1);
        MovieRank savedMovieRank1 = movieRankRepository.save(movieRank1);

        MovieRank movieRank2 = new MovieRank();
        movieRank2.setMovie(savedMovie1);
        movieRank2.setRank(10);
        movieRank2.setWatched(true);
        movieRank2.setUser(savedUser1);
        movieRank2.addMoviesList(savedMoviesList1);
        MovieRank savedMovieRank2 = movieRankRepository.save(movieRank2);

        MovieRank movieRank3 = new MovieRank();
        movieRank3.setMovie(savedMovie2);
        movieRank3.setRank(10);
        movieRank3.setWatched(true);
        movieRank3.setUser(savedUser1);
        movieRank3.addMoviesList(savedMoviesList1);
        MovieRank savedMovieRank3 = movieRankRepository.save(movieRank3);

        MovieRank movieRank4 = new MovieRank();
        movieRank4.setMovie(savedMovie3);
        movieRank4.setRank(10);
        movieRank4.setWatched(true);
        movieRank4.setUser(savedUser1);
        movieRank4.addMoviesList(savedMoviesList1);
        MovieRank savedMovieRank4 = movieRankRepository.save(movieRank4);

        MovieRank movieRank5 = new MovieRank();
        movieRank5.setMovie(savedMovie4);
        movieRank5.setRank(10);
        movieRank5.setWatched(true);
        movieRank5.setUser(savedUser1);
        movieRank5.addMoviesList(savedMoviesList1);
        MovieRank savedMovieRank5 = movieRankRepository.save(movieRank5);

        MovieRank movieRank6 = new MovieRank();
        movieRank6.setMovie(savedMovie5);
        movieRank6.setRank(10);
        movieRank6.setWatched(true);
        movieRank6.setUser(savedUser1);
        movieRank6.addMoviesList(savedMoviesList1);
        MovieRank savedMovieRank6 = movieRankRepository.save(movieRank6);

        MovieRank movieRank7 = new MovieRank();
        movieRank7.setMovie(savedMovie6);
        movieRank7.setRank(10);
        movieRank7.setWatched(true);
        movieRank7.setUser(savedUser1);
        movieRank7.addMoviesList(savedMoviesList1);
        MovieRank savedMovieRank7 = movieRankRepository.save(movieRank7);

        MovieRank movieRank8 = new MovieRank();
        movieRank8.setMovie(savedMovie7);
        movieRank8.setRank(10);
        movieRank8.setWatched(true);
        movieRank8.setUser(savedUser1);
        movieRank8.addMoviesList(savedMoviesList1);
        MovieRank savedMovieRank8 = movieRankRepository.save(movieRank8);
        
        MovieRank movieRank21 = new MovieRank();
        movieRank21.setMovie(savedMovie1);
        movieRank21.setRank(10);
        movieRank21.setWatched(true);
        movieRank21.setUser(savedUser2);
        movieRank21.addMoviesList(savedMoviesList2);
        MovieRank savedMovieRank21 = movieRankRepository.save(movieRank21);
    }
}
