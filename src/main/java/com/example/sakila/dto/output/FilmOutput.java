package com.example.sakila.dto.output;

import com.example.sakila.entities.Film;
import com.example.sakila.entities.Language;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class FilmOutput {
    private Short id;
    private String title;
    private String description;
    private Year release_year;
    private Language language;
    private Short original_language_id;
    private Short rental_duration;
    private BigDecimal rental_rate;
    private Short length;
    private List<ActorReferenceOutput> cast;
    private List<CategoryReferenceOutput> category;

    public static FilmOutput from(Film film) {
        return new FilmOutput(
                film.getId(),
                film.getTitle(),
                film.getDescription(),
                film.getRelease_year(),
                film.getLanguage(),
                film.getOriginal_language_id(),
                film.getRental_duration(),
                film.getRental_rate(),
                film.getLength(),
                film.getCast()
                        .stream()
                        .map(ActorReferenceOutput::from)
                        .collect(Collectors.toList()),
                film.getCategory()
                    .stream()
                    .map(CategoryReferenceOutput::from)
                    .collect(Collectors.toList())

        );
    }

}
