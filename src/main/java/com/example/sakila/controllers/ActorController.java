package com.example.sakila.controllers;

import com.example.sakila.dto.input.ActorInput;
import com.example.sakila.dto.output.ActorOutput;
import com.example.sakila.entities.Actor;
import com.example.sakila.repositories.ActorRepository;
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
@RequestMapping("/actors")
public class ActorController {

    @Autowired
    private ActorRepository actorRepository;

    @GetMapping
    public List<ActorOutput> readAll() {
        final var actors = actorRepository.findAll();
        return actors.stream()
                .map(ActorOutput::from)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ActorOutput readById(@PathVariable Short id) {
        return actorRepository.findById(id)
                .map(ActorOutput::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No such actor exists with id %d.", id)
                ));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ActorOutput create(@Validated(Create.class) @RequestBody ActorInput data) {
        final var actor = new Actor();
        actor.setFirst_name(data.getFirst_name());
        actor.setLast_name(data.getLast_name());
        final var saved = actorRepository.save(actor);
        return ActorOutput.from(saved);
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ActorOutput update(@PathVariable Short id,
                              // @Validated(ValidationGroup.Update.class)
                              @Valid @RequestBody ActorInput data) {
        Actor actor = actorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No such actor exists with id %d.", id)));

        if (data.getFirst_name() != null) {
            actor.setFirst_name(data.getFirst_name());
        }
        if (data.getLast_name() != null) {
            actor.setLast_name(data.getLast_name());
        }

        final var updated = actorRepository.save(actor);
        return ActorOutput.from(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Short id) {
        if (!actorRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    String.format("No such actor exists with id %d.", id));
        }
        actorRepository.deleteById(id);
    }
}


