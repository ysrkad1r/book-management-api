package com.kadiryasar.dto;

import com.kadiryasar.model.Book;
import com.kadiryasar.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DtoReview extends DtoBase{

    private String content;

    private Integer rating;

    private DtoUser createdByUser;

    private DtoBook toBook;

}
