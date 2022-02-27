package io.itamarc.mymovieslistapi.transfer;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MoviesListPayload {
    private Long id;

    private String title;

    private LocalDate created;

    private LocalDate updated;

    private MoviesListUserPayload user;
}
