package io.itamarc.mymovieslistapi.services;

import io.itamarc.mymovieslistapi.model.Movie;
import io.itamarc.mymovieslistapi.model.MovieRank;
import io.itamarc.mymovieslistapi.repositories.MovieRankRepository;
import io.itamarc.mymovieslistapi.repositories.MovieRepository;
import io.itamarc.mymovieslistapi.transfer.MoviePayload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.repositories.MoviesListRepository;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;

import java.util.LinkedHashSet;
import java.util.Set;

@Service
public class MoviesListServiceImpl implements MoviesListService {
    private final MoviesListRepository moviesListRepository;
    private final MovieRankRepository movieRankRepository;
    private final MovieRepository movieRepository;

    public MoviesListServiceImpl(MoviesListRepository moviesListRepository,
                                 MovieRankRepository movieRankRepository,
                                 MovieRepository movieRepository) {
        this.moviesListRepository = moviesListRepository;
        this.movieRankRepository = movieRankRepository;
        this.movieRepository = movieRepository;
    }

    public Page<MoviesListPayload> getMoviesLists(int page) {
        if (page < 1) {
            page = 1;
        }
        page--;
        Pageable pageable = PageRequest.of(page, 10, Sort.by("updated").descending()); // TODO Put the page size in some configuration file
        Page<MoviesList> pageData = moviesListRepository.findAll(pageable);
        return pageData.map(list -> MoviesListConverter.moviesListToMoviesListPayload(list));
    }

    public MoviesListPayload findById(Long id) {
        MoviesList moviesList = moviesListRepository.findById(id).orElse(null);
        if (moviesList != null) {
            return MoviesListConverter.moviesListToMoviesListPayload(moviesList);
        } else {
            return null;
        }
    }

    public MoviesListPayload newMoviesList(MoviesListPayload moviesListPayload) {
        MoviesList moviesList = MoviesListConverter.moviesListPayloadToMoviesList(moviesListPayload);
        moviesList.setMovieRanks(null);
        MoviesList savedMoviesList = moviesListRepository.save(moviesList);

        Set<MovieRank> movieRanks = new LinkedHashSet<>();
        for (MoviePayload moviePayload: moviesListPayload.getMovies()) {
            MovieRank movieRank = MoviesListConverter.moviePayloadToMovieRank(moviePayload);
            Movie savedMovie = movieRepository.save(movieRank.getMovie());
            movieRank.setMovie(savedMovie);
            movieRank.getMoviesLists().add(savedMoviesList);
            movieRank.setUser(savedMoviesList.getUser());
            MovieRank savedMovieRank = movieRankRepository.save(movieRank);
            movieRanks.add(savedMovieRank);
        }
        savedMoviesList.setMovieRanks(movieRanks);
        moviesListRepository.save(savedMoviesList);
        return MoviesListConverter.moviesListToMoviesListPayload(savedMoviesList);
    }
}
