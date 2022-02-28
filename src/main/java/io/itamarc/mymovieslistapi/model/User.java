package io.itamarc.mymovieslistapi.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true, exclude = { "moviesLists" })
@Table(name = "users")
public class User extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "registered")
    private LocalDate registered;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<MoviesList> moviesLists = new HashSet<>();
}
