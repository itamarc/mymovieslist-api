package io.itamarc.mymovieslistapi.services;

import io.itamarc.mymovieslistapi.model.*;
import io.itamarc.mymovieslistapi.transfer.MovieGenrePayload;
import io.itamarc.mymovieslistapi.transfer.MoviePayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.UserPayload;

import java.util.HashSet;
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
                .movies(movieRanksToMoviePayloads(moviesList.getMovieRanks()))
                .build();
    }

    public static Set<MoviePayload> movieRanksToMoviePayloads(Set<MovieRank> movieRanks) {
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
                .genres(movieGenresToMovieGenrePayloads(movieRank.getMovie().getGenres()))
                .rank(movieRank.getRank())
                .watched(movieRank.isWatched())
                .build();
    }

    public static Set<MovieGenrePayload> movieGenresToMovieGenrePayloads(Set<MovieGenre> genres) {
        Set<MovieGenrePayload> movieGenrePayloads = new HashSet<>();
        genres.forEach(movieGenre -> movieGenrePayloads.add(
                MovieGenrePayload.builder()
                        .id(movieGenre.getId())
                        .name(movieGenre.getName())
                        .build()
            )
        );
        return movieGenrePayloads;
    }

    public static Set<MovieGenre> movieGenrePayloadsToMovieGenres(Set<MovieGenrePayload> genrePayloads, Movie movie) {
        Set<MovieGenre> movieGenres = new HashSet<>();
        genrePayloads.forEach(genrePayload -> {
            MovieGenre movieGenre = movieGenrePayloadToMovieGenre(genrePayload);
            movieGenre.getMovies().add(movie);
            movieGenres.add(movieGenre);
        });
        return movieGenres;
    }

    public static MovieGenre movieGenrePayloadToMovieGenre(MovieGenrePayload genrePayload) {
        return new MovieGenre(genrePayload.getId(), genrePayload.getName());
    }

    public static MovieRank moviePayloadToMovieRank(MoviePayload moviePayload) {
        Movie movie = new Movie();
        movie.setId(moviePayload.getId());
        movie.setTitle(moviePayload.getTitle());
        movie.setDescription(moviePayload.getDescription());
        movie.setImageUrl(moviePayload.getImageUrl());
        movie.setYear(moviePayload.getYear());
        movie.setGenres(movieGenrePayloadsToMovieGenres(moviePayload.getGenres(), movie));

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
