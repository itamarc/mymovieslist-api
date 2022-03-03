package io.itamarc.mymovieslistapi.services;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.itamarc.mymovieslistapi.model.MovieRank;
import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.MoviesListRepository;
import io.itamarc.mymovieslistapi.transfer.MoviePayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

@Service
public class MoviesListServiceImpl implements MoviesListService {
    private final MoviesListRepository moviesListRepository;

    public MoviesListServiceImpl(MoviesListRepository moviesListRepository) {
        this.moviesListRepository = moviesListRepository;
    }

    public Set<MoviesListPayload> getMoviesLists(int page) {
        Set<MoviesListPayload> moviesLists = new LinkedHashSet<>();
        if (page < 1) {
            page = 1;
        }
        // TODO get only a page of movies lists
        Iterable<MoviesList> iter = moviesListRepository.findAll();
        iter.forEach(list -> moviesLists.add(moviesListToMoviesListPayload(list)));
        return moviesLists;
    }

    private MoviesListPayload moviesListToMoviesListPayload(MoviesList moviesList) {
        return MoviesListPayload.builder()
            .id(moviesList.getId())
            .title(moviesList.getTitle())
            .created(moviesList.getCreated())
            .updated(moviesList.getUpdated())
            .user(userToUserPayload(moviesList.getUser()))
            .movies(moviesToMoviesPayload(moviesList.getMovieRanks()))
            .build();
}

    public MoviesListPayload findById(Long id) {
        MoviesList moviesList = moviesListRepository.findById(id).orElse(null);
        if (moviesList != null) {
            return moviesListToMoviesListPayload(moviesList);
        } else {
            return null;
        }
    }

    private Set<MoviePayload> moviesToMoviesPayload(Set<MovieRank> movieRanks) {
        Set<MoviePayload> movies = new LinkedHashSet<>();
        movieRanks.forEach(movieRank -> movies.add(movieRankToMoviePayload(movieRank)));
        return movies;
    }

    private MoviePayload movieRankToMoviePayload(MovieRank movieRank) {
        return MoviePayload.builder()
            .id(movieRank.getMovie().getId())
            .title(movieRank.getMovie().getTitle())
            .description(movieRank.getMovie().getDescription())
            .year(movieRank.getMovie().getYear())
            .imageUrl(movieRank.getMovie().getImageUrl())
            .rank(movieRank.getRank())
            .watched(movieRank.isWatched())
            .build();
    }

    private UserPayload userToUserPayload(User user) {
        return UserPayload.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .registered(user.getRegistered())
                .build();
    }
}
