package com.example.sakila.entities;

import jakarta.persistence.*;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "film")
@Getter
@Setter
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "release_year")
    private Year release_year;

    @Column(name = "original_language_id")
    private Short original_language_id;

    @Column(name = "rental_duration")
    private Short rental_duration;

    @Generated
    @Column(name = "rental_rate")
    private BigDecimal rental_rate;

    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "length")
    private Short length;

    @Column(name = "special_features")
    private String special_features;

    @ManyToMany (mappedBy = "films")
    private List<Actor> cast = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "film_category",
            joinColumns = {@JoinColumn(name = "film_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")}
    )
    private List<Category> category = new ArrayList<>();


}
