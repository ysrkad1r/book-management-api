package com.kadiryasar.security;

import com.kadiryasar.model.Review;
import com.kadiryasar.model.User;
import com.kadiryasar.repository.ReviewRepository;
import com.kadiryasar.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component("reviewSecurity")
@RequiredArgsConstructor
public class ReviewSecurity {

    private final ReviewRepository reviewRepository;
    private final SecurityUtil securityUtil;

    public boolean isOwner(Long id){
        String username = securityUtil.getCurrentUsername();
        if (username == null){
            return false;
        }

        return reviewRepository.findById(id)
                .map(Review::getCreatedByUser)
                .map(User::getId)
                .map(ownerId -> ownerId.equals(securityUtil.getCurrentUserId()))
                .orElse(false);
    }

}
