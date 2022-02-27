package io.itamarc.mymovieslistapi.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import io.itamarc.mymovieslistapi.model.MoviesList;
import io.itamarc.mymovieslistapi.model.User;
import io.itamarc.mymovieslistapi.repositories.MoviesListRepository;
import io.itamarc.mymovieslistapi.transfer.MoviesListPayload;
import io.itamarc.mymovieslistapi.transfer.MoviesListUserPayload;

@Service
public class MoviesListServiceImpl implements MoviesListService {
    private final MoviesListRepository moviesListRepository;

    public MoviesListServiceImpl(MoviesListRepository moviesListRepository) {
        this.moviesListRepository = moviesListRepository;
    }

    public Set<MoviesListPayload> getMoviesLists(int page) {
        Set<MoviesListPayload> moviesLists = new HashSet<>();
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
            .build();
}

    public MoviesListPayload findById(Long id) {
        MoviesList moviesList = moviesListRepository.findById(id).orElse(null);
        if (moviesList != null) {
            return MoviesListPayload.builder()
                    .id(moviesList.getId())
                    .title(moviesList.getTitle())
                    .created(moviesList.getCreated())
                    .updated(moviesList.getUpdated())
                    .user(userToUserPayload(moviesList.getUser()))
                    .build();
        } else {
            return null;
        }
    }

    private MoviesListUserPayload userToUserPayload(User user) {
        return MoviesListUserPayload.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .imageUrl(user.getImageUrl())
                .registered(user.getRegistered())
                .build();
    }
}
