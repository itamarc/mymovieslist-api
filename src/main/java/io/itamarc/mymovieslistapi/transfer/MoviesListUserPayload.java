package io.itamarc.mymovieslistapi.transfer;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoviesListUserPayload {
    private Long id;

    private String name;

    private String email;

    private String imageUrl;

    private LocalDate registered;
}
