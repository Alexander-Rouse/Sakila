package com.example.sakila.dto.output;

import com.example.sakila.entities.Actor;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ActorOutput {
    private Short id;
    private String first_name;
    private String last_name;
    private List<FilmReferenceOutput> films;

    public static ActorOutput from(Actor actor) {
        return new ActorOutput(
                actor.getId(),
                actor.getFirst_name(),
                actor.getLast_name(),
                actor.getFilms()
                        .stream()
                        .map(FilmReferenceOutput::from)
                        .collect(Collectors.toList())
        );
    }

    public String getFullName() {
        return first_name + " " + last_name;
    }
}
