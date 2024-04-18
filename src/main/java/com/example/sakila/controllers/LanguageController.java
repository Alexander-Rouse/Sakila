package com.example.sakila.controllers;

import com.example.sakila.dto.output.LanguageOutput;
import com.example.sakila.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
public class LanguageController {

    @Autowired
    private LanguageRepository languageRepository;

    @GetMapping("/{id}")
    public LanguageOutput readById(@PathVariable Byte id) {
        return languageRepository.findById(id)
                .map(LanguageOutput::from)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("No such language with id %d.", id)
                ));
    }
}
