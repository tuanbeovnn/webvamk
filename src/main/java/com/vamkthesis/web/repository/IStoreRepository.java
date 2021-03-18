package com.vamkthesis.web.repository;

import com.vamkthesis.web.entity.StoreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IStoreRepository extends JpaRepository<StoreEntity, Long> {
    Page<StoreEntity> findAll(Pageable pageable);

}
