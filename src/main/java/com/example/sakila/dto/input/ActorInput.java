package com.example.sakila.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import static com.example.sakila.dto.input.ValidationGroup.Create;
@Data // equivalent of having Getter / Setter / No Args Constructor / Adds Hash Function
// and adds a two-string function
public class ActorInput {
    //text after group only applies if used to Create
    @NotNull(groups = {Create.class})
    @Size(min = 1, max = 45)
    private String first_name;
    @NotNull(groups = {Create.class})
    @Size(min = 1, max = 45)
    private String last_name;
}

