package com.kadiryasar.repository;

import com.kadiryasar.model.Review;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    public Page<Review> findAll(@NonNull Pageable pageable);

    public Page<Review> findByToBook_Id(Long bookId, @NonNull Pageable pageable);

    public Page<Review> findByCreatedByUser_Id(Long userId, @NonNull Pageable pageable);


}
