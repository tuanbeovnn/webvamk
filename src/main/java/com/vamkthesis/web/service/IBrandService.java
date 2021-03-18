package com.vamkthesis.web.service;


import com.vamkthesis.web.dto.BrandDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IBrandService {
    BrandDto save(BrandDto brandDto);
    List<BrandDto> findAll(Pageable pageable);
}
