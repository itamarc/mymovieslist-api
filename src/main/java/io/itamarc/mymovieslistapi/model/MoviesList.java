package io.itamarc.mymovieslistapi.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movies_lists")
public class MoviesList extends BaseEntity {
    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany()
    private Set<MovieRank> movies = new HashSet<>();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<MovieRank> getMovies() {
        return movies;
    }

    public void setMovies(Set<MovieRank> movies) {
        this.movies = movies;
    }
}
