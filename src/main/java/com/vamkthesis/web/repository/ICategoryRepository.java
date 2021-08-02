package com.vamkthesis.web.repository;


import com.vamkthesis.web.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    CategoryEntity findOneByCode(String code);

    List<CategoryEntity> findAll();
}
