package com.kadiryasar.service.Impl;

import com.kadiryasar.dto.*;
import com.kadiryasar.exception.BaseException;
import com.kadiryasar.exception.ErrorMessage;
import com.kadiryasar.exception.MessageType;
import com.kadiryasar.model.Book;
import com.kadiryasar.model.Review;
import com.kadiryasar.model.User;
import com.kadiryasar.repository.BookRepository;
import com.kadiryasar.repository.ReviewRepository;
import com.kadiryasar.repository.UserRepository;
import com.kadiryasar.service.IReviewService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    private DtoReview toDto(Review review) {
        DtoReview dto = new DtoReview();

        DtoUser dtoUser = new DtoUser();
        if (review.getCreatedByUser() != null) {
            BeanUtils.copyProperties(review.getCreatedByUser(), dtoUser);
            dtoUser.setRoleType(review.getCreatedByUser().getRoleType());
        }

        DtoBook dtoBook = new DtoBook();
        if (review.getToBook() != null) {
            BeanUtils.copyProperties(review.getToBook(), dtoBook);

            DtoUser dtoBookOwner = new DtoUser();
            if (review.getToBook().getCreatedBy() != null) {
                BeanUtils.copyProperties(review.getToBook().getCreatedBy(), dtoBookOwner);
                dtoBookOwner.setRoleType(review.getToBook().getCreatedBy().getRoleType());
            }
            dtoBook.setCreatedBy(dtoBookOwner);
        }

        BeanUtils.copyProperties(review, dto);
        dto.setCreatedByUser(dtoUser);
        dto.setToBook(dtoBook);
        return dto;
    }

    @Transactional
    @Override
    public DtoReview addReviewToBook(DtoReviewIU iu) {

        User user = userRepository.findById(iu.getCreatedByUserId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.USER_NOT_FOUND, iu.getCreatedByUserId().toString())
                ));

        Book book = bookRepository.findById(iu.getToBookId())
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.BOOK_NOT_FOUND, iu.getToBookId().toString())
                ));


        Review review = new Review();
        BeanUtils.copyProperties(iu, review);
        review.setCreatedByUser(user);
        review.setToBook(book);
        review.setCreateTime(new Date());

        Review savedReview = reviewRepository.save(review);

        return toDto(savedReview);
    }

    @Override
    public DtoReview updateReview(Long reviewId, DtoReviewUpdateIU dtoReviewUpdateIU) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.REVIEW_NOT_FOUND,reviewId.toString())
        ));

        if (dtoReviewUpdateIU.getContent()!=null){
            review.setContent(dtoReviewUpdateIU.getContent());
        }
        if (dtoReviewUpdateIU.getRating()!=null){
            review.setRating(dtoReviewUpdateIU.getRating());
        }

        Review savedReview = reviewRepository.save(review);

        return toDto(savedReview);
    }

    @Transactional
    @Override
    public DtoReview deleteReview(Long reviewId) {

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new BaseException(
                new ErrorMessage(MessageType.REVIEW_NOT_FOUND,reviewId.toString())
        ));

        var auth = SecurityContextHolder.getContext().getAuthentication();
        String username = (auth != null) ? auth.getName() : null;

        boolean isAdmin = auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")); // hasRole('ADMIN') ile uyumlu

        boolean isOwner = review.getCreatedByUser() != null && review.getCreatedByUser().getUsername().equals(username);

        if (!(isAdmin || isOwner)) {
            throw new BaseException(new ErrorMessage(MessageType.INSUFFICIENT_AUTHORITY, String.valueOf(reviewId)));}

        reviewRepository.deleteById(reviewId);

        return toDto(review);
    }

    @Override
    public Page<DtoReview> getReviewsByBook(Long bookId, Pageable pageable) {
        return reviewRepository.findByToBook_Id(bookId, pageable).map(this::toDto);
    }

    @Override
    public Page<DtoReview> getReviewsByUser(Long userId, Pageable pageable) {
        return reviewRepository.findByCreatedByUser_Id(userId , pageable).map(this::toDto);
    }
}
