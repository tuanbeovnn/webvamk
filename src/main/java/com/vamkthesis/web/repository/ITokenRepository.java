package com.vamkthesis.web.repository;

import com.vamkthesis.web.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ITokenRepository extends JpaRepository<TokenEntity, Long> {
    @Query(value = "select envoke from token where access_token = ?", nativeQuery = true)
    boolean isTokenEnvoke(String token);

    @Query(value = "select * from token where user_id = ? order by created_date limit 0,1", nativeQuery = true)
    TokenEntity findOneUserId(Long id);

    @Query(value = "select * from token where refresh_token = ? order by created_date limit 0,1", nativeQuery = true)
    TokenEntity findByRefreshToken(String token);
}
