package io.itamarc.mymovieslistapi.services;

import io.itamarc.mymovieslistapi.model.Movie;
import io.itamarc.mymovieslistapi.model.MovieRank;
import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.transfer.MoviePayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

import java.util.LinkedHashSet;
import java.util.Set;

public class MoviesListConverter {
    public static MoviesListPayload moviesListToMoviesListPayload(MoviesList moviesList) {
        return moviesListToMoviesListPayload(moviesList,
            UserConverter.userToUserPayload(moviesList.getUser())
        );
    }

    public static MoviesListPayload moviesListToMoviesListPayload(MoviesList moviesList, UserPayload userPayload) {
        return MoviesListPayload.builder()
                .id(moviesList.getId())
                .title(moviesList.getTitle())
                .created(moviesList.getCreated())
                .updated(moviesList.getUpdated())
                .user(userPayload)
                .moviesCount(moviesList.getMoviesCount())
                .movies(moviesToMoviesPayload(moviesList.getMovieRanks()))
                .build();
    }

    public static Set<MoviePayload> moviesToMoviesPayload(Set<MovieRank> movieRanks) {
        Set<MoviePayload> movies = new LinkedHashSet<>();
        movieRanks.forEach(movieRank -> movies.add(movieRankToMoviePayload(movieRank)));
        return movies;
    }

    public static MoviePayload movieRankToMoviePayload(MovieRank movieRank) {
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

    public static MovieRank moviePayloadToMovieRank(MoviePayload moviePayload) {
        Movie movie = new Movie();
        movie.setId(moviePayload.getId());
        movie.setTitle(moviePayload.getTitle());
        movie.setDescription(moviePayload.getDescription());
        movie.setImageUrl(moviePayload.getImageUrl());
        movie.setYear(moviePayload.getYear());

        MovieRank movieRank = new MovieRank();
        movieRank.setMovie(movie);
        movieRank.setRank(moviePayload.getRank());
        movieRank.setWatched(moviePayload.isWatched());

        return movieRank;
    }

    public static MovieRank moviePayloadToMovieRank(MoviePayload moviePayload,
                                                    User user,
                                                    MoviesList moviesList) {
        MovieRank movieRank = moviePayloadToMovieRank(moviePayload);
        movieRank.setUser(user);
        movieRank.getMoviesLists().add(moviesList);
        return movieRank;
    }

    public static MoviesList moviesListPayloadToMoviesList(MoviesListPayload moviesListPayload) {
        return moviesListPayloadToMoviesList(moviesListPayload,
                UserConverter.userPayloadToUser(moviesListPayload.getUser())
        );
    }

    public static MoviesList moviesListPayloadToMoviesList(MoviesListPayload moviesListPayload,
                                                           User user) {
        MoviesList moviesList = new MoviesList();
        moviesList.setId(moviesListPayload.getId());
        moviesList.setTitle(moviesListPayload.getTitle());
        moviesList.setCreated(moviesListPayload.getCreated());
        moviesList.setUpdated(moviesListPayload.getUpdated());
        moviesList.setUser(user);
        if (!moviesListPayload.getMovies().isEmpty()) {
            Set<MovieRank> movieRanks = new LinkedHashSet<>(moviesListPayload.getMovies().size());
            moviesListPayload.getMovies().forEach(moviePayload ->
                movieRanks.add(moviePayloadToMovieRank(moviePayload, user, moviesList))
            );
            moviesList.setMovieRanks(movieRanks);
        }
        return moviesList;
    }
}
