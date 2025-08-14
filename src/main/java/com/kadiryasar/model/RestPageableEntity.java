package com.kadiryasar.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RestPageableEntity<T> {

    private List<T> content;

    private int pageNumber;

    private int pageSize;

    private Long totalElements;

    private int totalPages;

    private boolean first;

    private boolean last;

    private boolean hasNext;

    private boolean hasPrevious;

    private String sort;

}
