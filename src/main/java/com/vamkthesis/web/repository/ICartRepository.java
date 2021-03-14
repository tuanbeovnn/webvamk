package com.vamkthesis.web.repository;

import com.vamkthesis.web.entity.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ICartRepository extends JpaRepository<CartEntity, Long> {

    @Query(value = "SELECT * FROM carts INNER JOIN users on users.id = carts.user_id WHERE users.id = ?1 ORDER BY carts.created_date desc LIMIT 0,1",nativeQuery = true)
    List<CartEntity> findALlCartByUser(Long id);
}
