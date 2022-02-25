package io.itamarc.mymovieslistapi.transfer;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class UserPayload {
    private Long id;

    private String name;

    private String email;

    private String password;

    private String imageUrl;

    private LocalDate registered;

    private Set<UserMoviesListPayload> moviesLists = new HashSet<>();
}
