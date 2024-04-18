package com.example.sakila.controllers;

import com.example.sakila.dto.input.FilmInput;
import com.example.sakila.dto.output.FilmOutput;
import com.example.sakila.entities.Film;
import com.example.sakila.entities.Language;
import com.example.sakila.repositories.FilmRepository;
import com.example.sakila.repositories.LanguageRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.sakila.dto.input.ValidationGroup.Create;


@RestController
@RequestMapping("/films")
public class FilmController {

    @Autowired
    private FilmRepository filmRepository;
    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping
    public List<FilmOutput> readAll() {
        final var films = filmRepository.findAll();
        return films.stream()
                .map(FilmOutput::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public FilmOutput readById(@PathVariable Short id) {
        return filmRepository.findById(id)
                .map(FilmOutput::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No such film with id %d.", id)
                ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FilmOutput create(@Validated(Create.class) @RequestBody FilmInput data) {

        final var film = new Film();
        film.setTitle(data.getTitle());
        film.setRelease_year(data.getRelease_year());
        film.setRental_rate(data.getRental_rate());
        film.setRental_duration(data.getRental_duration());

        if(data.getLanguage_id() == null || !languageRepository.existsById(
                data.getLanguage_id())) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("No such language with id %d.", data.getLanguage_id())
            );
        }
        Language language = languageRepository.findById(data.getLanguage_id())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                    String.format("No such language with id %d.", data.getLanguage_id())
            ));
        film.setLanguage(language);

        final var saved = filmRepository.save(film);
        return FilmOutput.from(saved);
    }


    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public FilmOutput update(@PathVariable Short id,
                            // @Validated(ValidationGroup.Update.class)
                             @Valid@RequestBody FilmInput data) {
        Film film = filmRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No such film with id %d.", +id)) );

        if (data.getTitle() != null) {
            film.setTitle(data.getTitle());
        }
        if (data.getRelease_year() != null) {
            film.setRelease_year(data.getRelease_year());
        }
        if (data.getRental_rate() != null) {
            film.setRental_rate(data.getRental_rate());
        }
        if (data.getLanguage_id() != null) {
            if (!languageRepository.existsById(data.getLanguage_id())) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No such language with id %d.", data.getLanguage_id()));
            }
            Language language = languageRepository.findById(data.getLanguage_id())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.BAD_REQUEST,
                            String.format("No such language with id %d.", data.getLanguage_id())));
            film.setLanguage(language);
        }
        final var updated = filmRepository.save(film);
        return FilmOutput.from(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Short id) {
        if (!filmRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("No such film exists with id %d.", + id));
            }
        filmRepository.deleteById(id);
    }
}

