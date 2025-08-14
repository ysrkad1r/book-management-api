package com.kadiryasar.service;


import com.kadiryasar.dto.DtoReview;
import com.kadiryasar.dto.DtoReviewIU;
import com.kadiryasar.dto.DtoReviewUpdateIU;
import com.kadiryasar.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReviewService {

    public DtoReview addReviewToBook(DtoReviewIU dtoReviewIU);

    public DtoReview updateReview(Long reviewId, DtoReviewUpdateIU dtoReviewUpdateIU);

    public DtoReview deleteReview(Long reviewId);

    public Page<DtoReview> getReviewsByBook(Long bookId, Pageable pageable);

    public Page<DtoReview> getReviewsByUser(Long userId, Pageable pageable);

}
