package com.kadiryasar.controller.Impl;

import com.kadiryasar.controller.IRestReviewController;
import com.kadiryasar.controller.RestBaseController;
import com.kadiryasar.controller.RootEntity;
import com.kadiryasar.dto.DtoReview;
import com.kadiryasar.dto.DtoReviewIU;
import com.kadiryasar.dto.DtoReviewUpdateIU;
import com.kadiryasar.service.IReviewService;
import com.kadiryasar.model.RestPageableEntity;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/rest/api/user")
@RequiredArgsConstructor
public class RestReviewControllerImpl extends RestBaseController implements IRestReviewController {

    private final IReviewService reviewService;

    @PostMapping(path = "/reviews/save-review")
    @Override
    public RootEntity<DtoReview> addReviewToBook(@Valid @RequestBody DtoReviewIU dtoReviewIU) {
        return ok(reviewService.addReviewToBook(dtoReviewIU));
    }

    @PutMapping(path = "/reviews/update-review/{id}")
    @PreAuthorize("@reviewSecurity.isOwner(#reviewId)")
    @Override
    public RootEntity<DtoReview> updateReview(@PathVariable(name = "id") Long reviewId, @Valid @RequestBody DtoReviewUpdateIU dtoReviewUpdateIU) {
        return ok(reviewService.updateReview(reviewId,dtoReviewUpdateIU));
    }

    @DeleteMapping(path = "/reviews/delete-review/{id}")
    @PreAuthorize("hasRole('ADMIN') or @reviewSecurity.isOwner(#reviewId)")
    @Override
    public RootEntity<DtoReview> deleteReview(@PathVariable(name = "id") Long reviewId) {
        return ok(reviewService.deleteReview(reviewId));
    }

    @GetMapping(path = "/reviews/list/by-book/{id}")
    @Override
    public RootEntity<RestPageableEntity<DtoReview>> getReviewsByBook(@PathVariable(name = "id") Long bookId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size,
                                                                      @RequestParam(defaultValue = "id") String sortField,
                                                                      @RequestParam(defaultValue = "false") boolean asc) {
        Pageable pageable = toPageable(page,size,sortField,asc);
        Page<DtoReview> reviewPage = reviewService.getReviewsByBook(bookId, pageable);

        return ok(toPageableEntity(reviewPage));
    }

    @GetMapping(path = "/reviews/list/by-user/{id}")
    @Override
    public RootEntity<RestPageableEntity<DtoReview>> getReviewsByUser(@PathVariable(name = "id") Long userId,
                                                                      @RequestParam(defaultValue = "0") int page,
                                                                      @RequestParam(defaultValue = "5") int size,
                                                                      @RequestParam(defaultValue = "id") String sortField,
                                                                      @RequestParam(defaultValue = "false") boolean asc) {
        Pageable pageable = toPageable(page,size,sortField,asc);
        Page<DtoReview> reviewPage = reviewService.getReviewsByUser(userId, pageable);

        return ok(toPageableEntity(reviewPage));
    }


}
