package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.BrandEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    BrandEntity findOneByCode(String code);
}
