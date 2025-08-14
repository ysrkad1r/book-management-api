package com.kadiryasar.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Getter
@Setter
public class DtoReviewIU {

    @NotNull
    private String content;

    @NotNull
    @Range(min = 0,max = 5)
    private Integer rating;

    @NotNull
    private Long createdByUserId;

    @NotNull
    private Long toBookId;
}
