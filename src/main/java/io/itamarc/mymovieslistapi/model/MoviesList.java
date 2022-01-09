package io.itamarc.mymovieslistapi.model;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies_lists")
public class MoviesList extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Column(name = "created")
    private LocalDate created;

    @Column(name = "updated")
    private LocalDate updated;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "moviesLists", fetch = FetchType.LAZY)
    private Set<MovieRank> movieRanks = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getCreated() {
        return created;
    }

    public void setCreated(LocalDate created) {
        this.created = created;
    }

    public LocalDate getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDate updated) {
        this.updated = updated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void addMovieRank(MovieRank movieRank) {
        this.movieRanks.add(movieRank);
        if (!movieRank.getMoviesLists().contains(this)) {
            movieRank.addMoviesList(this);
        }
    }

    public Set<MovieRank> getMovieRanks() {
        return movieRanks;
    }

    public void setMovieRanks(Set<MovieRank> movieRanks) {
        this.movieRanks = movieRanks;
    }
}
