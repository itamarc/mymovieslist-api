package io.itamarc.mymovieslistapi.model;

import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = { "movieRanks" })
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

    public void addMovieRank(MovieRank movieRank) {
        this.movieRanks.add(movieRank);
        if (!movieRank.getMoviesLists().contains(this)) {
            movieRank.addMoviesList(this);
        }
    }
}
