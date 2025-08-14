package com.kadiryasar.repository;

import com.kadiryasar.model.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Modifying //bu ney bakarim bilmiyom
    @Transactional //silme islemi icin zorunlu
    @Query("DELETE FROM RefreshToken rt WHERE rt.user.id = :userId") //bu query sorgusuna gore silme islemi gerceklesek
    void deleteAllByUserId(@Param("userId") Long userId);

}
