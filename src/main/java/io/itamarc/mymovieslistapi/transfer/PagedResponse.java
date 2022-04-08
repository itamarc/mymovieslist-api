package io.itamarc.mymovieslistapi.transfer;

import com.fasterxml.jackson.annotation.JsonView;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PagedResponse<T> {
    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private T content;
    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private int currentPage;
    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private int pageSize;
    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private long totalElements;
    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private int totalPages;
    @JsonView(value = { UserViews.UserBasic.class, MoviesListViews.MoviesListBasic.class })
    private boolean last;
}
