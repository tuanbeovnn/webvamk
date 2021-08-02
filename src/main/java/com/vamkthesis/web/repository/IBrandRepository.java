package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.BrandEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IBrandRepository extends JpaRepository<BrandEntity, Long> {
    BrandEntity findOneByCode(String code);

    Page<BrandEntity> findAll(Pageable pageable);
}
