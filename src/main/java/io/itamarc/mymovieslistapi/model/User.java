package io.itamarc.mymovieslistapi.model;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true, exclude = { "moviesLists" })
@EqualsAndHashCode(callSuper = true, exclude = { "moviesLists" })
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class User extends BaseEntity {
    @Builder
    public User(Long id,
                String name,
                String email,
                String password,
                String imageUrl,
                Boolean emailVerified,
                LocalDate registered) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.imageUrl = imageUrl;
        this.emailVerified = emailVerified;
        this.registered = registered;
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified;

    @Column
    private LocalDate registered;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<MoviesList> moviesLists = new LinkedHashSet<>();

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;
}
