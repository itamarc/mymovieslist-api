package io.itamarc.mymovieslistapi.model;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie_ranks")
public class MovieRank extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(name = "movie_ranks_movies_lists", joinColumns = @JoinColumn(name = "movie_rank_id"),
    inverseJoinColumns = @JoinColumn(name = "movies_list_id"))
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private Set<MoviesList> moviesLists = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "rank")
    private Integer rank;

    @Column(name = "watched")
    private boolean watched;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addMoviesList(MoviesList moviesList) {
        this.moviesLists.add(moviesList);
        moviesList.addMovieRank(this);
    }

    public Set<MoviesList> getMoviesLists() {
        return moviesLists;
    }

    public void setMoviesLists(Set<MoviesList> moviesLists) {
        this.moviesLists = moviesLists;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public boolean isWatched() {
        return watched;
    }

    public void setWatched(boolean watched) {
        this.watched = watched;
    }
}
