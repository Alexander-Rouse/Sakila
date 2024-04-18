package com.example.sakila.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Year;

import static com.example.sakila.dto.input.ValidationGroup.Create;
@Data // equivalent of having Getter / Setter / No Args Constructor / Adds Hash Function
// and adds a two-string function
public class FilmInput {
    //text after group only applies if used to Create
    @NotNull(groups = {Create.class})
    @Size(min = 1, max = 45)
    private String title;

    @NotNull(groups = {Create.class})
    //@Min(1901)
    //@Max(2155)
    private Year release_year;

    @NotNull(groups = {Create.class})
    private Byte language_id;

    @NotNull(groups = {Create.class})
    private BigDecimal rental_rate;

    @NotNull(groups = {Create.class})
    private Short rental_duration;

}
