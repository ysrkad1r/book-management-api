package com.kadiryasar.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoBookUpdateIU {

    private String title;

    private String author;

    private String description;

}
