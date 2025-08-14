package com.kadiryasar.dto;

import com.kadiryasar.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoBook extends DtoBase{

    private String title;

    private String author;

    private String description;

    private DtoUser createdBy;

}
