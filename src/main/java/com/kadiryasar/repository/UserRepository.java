package com.kadiryasar.repository;

import com.kadiryasar.model.User;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

    //@Query("SELECT u FROM User u")
    Page<User> findAll(@NonNull Pageable pageable);

}
