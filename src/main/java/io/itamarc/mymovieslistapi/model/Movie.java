package io.itamarc.mymovieslistapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "movies")
public class Movie extends BaseEntity {
    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "year")
    private Integer year;

    @Column(name = "image_url")
    private String imageUrl;
}
