package com.kadiryasar.repository;

import com.kadiryasar.model.Book;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    public Page<Book> findAll(@NonNull Pageable pageable);

    public Page<Book> findByCreatedById(Long id, Pageable pageable);

}
