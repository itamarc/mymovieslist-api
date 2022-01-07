package io.itamarc.mymovieslistapi.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie_ranks")
public class MovieRank extends BaseEntity {

    @ManyToOne
    private User user;

    @ManyToMany
    private Set<MoviesList> moviesLists = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private char rank;

    private boolean watched;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<MoviesList> getMoviesList() {
        return moviesLists;
    }

    public void setMoviesList(Set<MoviesList> moviesLists) {
        this.moviesLists = moviesLists;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public char getRank() {
        return rank;
    }

    public void setRank(char rank) {
        this.rank = rank;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
