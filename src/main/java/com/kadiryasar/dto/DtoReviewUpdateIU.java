package com.kadiryasar.dto;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;


@Getter
@Setter
public class DtoReviewUpdateIU {

    private String content;

    @Range(min = 0,max = 5)
    private Integer rating;

}
