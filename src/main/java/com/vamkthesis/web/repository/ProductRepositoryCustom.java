package com.vamkthesis.web.repository;


import com.vamkthesis.web.builder.ProductSearchBuilder;
import com.vamkthesis.web.entity.ProductEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductRepositoryCustom {
    List<ProductEntity> findAll(ProductSearchBuilder fieldSearch, Pageable pageable);

}
