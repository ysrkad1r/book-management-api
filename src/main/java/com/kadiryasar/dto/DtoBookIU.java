package com.kadiryasar.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoBookIU {

    @NotNull
    private String title;

    @NotNull
    private String author;

    @NotNull
    private String description;

    @NotNull
    private Long userId;

}
