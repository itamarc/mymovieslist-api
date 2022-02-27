package io.itamarc.mymovieslistapi.transfer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class UserPayload {
    private Long id;

    private String name;

    private String email;

    @JsonIgnore
    private String password;

    private String imageUrl;

    private LocalDate registered;

    private Set<UserMoviesListPayload> moviesLists = new HashSet<>();
}
