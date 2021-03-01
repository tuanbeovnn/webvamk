package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IRatingRepository extends JpaRepository<RatingEntity, Long> {
    @Query(value = "SELECT AVG(rating) FROM ratings where product_id = ?", nativeQuery = true)
    Double avgRating(long id);

    @Query(value = "SELECT * from ratings WHERE user_id = ?1 and product_id = ?2", nativeQuery = true)
    RatingEntity findOneUserAndProduct(long idUser, long idProduct);
}
