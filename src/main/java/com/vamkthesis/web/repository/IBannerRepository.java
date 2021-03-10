package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.BannerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBannerRepository extends JpaRepository<BannerEntity, Long> {
    @Query(value = "select * from banners where banners.position = ?",nativeQuery = true)
    List<BannerEntity> findAllByPosition(int position);
}
