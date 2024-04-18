package com.example.sakila.dto.output;

import com.example.sakila.entities.Film;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Year;
@Getter
@AllArgsConstructor

public class FilmReferenceOutput {
    private Short id;
    private String title;
    private Year release_year;

    public static FilmReferenceOutput from (Film film) {
        return new FilmReferenceOutput(film.getId(), film.getTitle(), film.getRelease_year());
    }
}
