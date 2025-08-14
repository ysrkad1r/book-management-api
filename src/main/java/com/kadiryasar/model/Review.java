package com.kadiryasar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseEntity{

    @Column(name = "content")
    private String content;

    @Column(name = "rating")
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id", nullable = false)
    private User createdByUser;

    @ManyToOne
    @JoinColumn(name = "to_book_id", nullable = false)
    private Book toBook;



}
