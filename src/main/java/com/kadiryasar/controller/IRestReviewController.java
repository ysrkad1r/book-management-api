package com.kadiryasar.controller;

import com.kadiryasar.dto.DtoReview;
import com.kadiryasar.dto.DtoReviewIU;
import com.kadiryasar.dto.DtoReviewUpdateIU;
import com.kadiryasar.model.RestPageableEntity;

public interface IRestReviewController {

    public RootEntity<DtoReview> addReviewToBook(DtoReviewIU dtoReviewIU);

    public RootEntity<DtoReview> updateReview(Long reviewId, DtoReviewUpdateIU dtoReviewUpdateIU);

    public RootEntity<DtoReview> deleteReview(Long reviewId);

    public RootEntity<RestPageableEntity<DtoReview>> getReviewsByBook(Long bookId, int page, int size , String sortField, boolean asc);

    public RootEntity<RestPageableEntity<DtoReview>> getReviewsByUser(Long userId, int page, int size , String sortField, boolean asc);
}
