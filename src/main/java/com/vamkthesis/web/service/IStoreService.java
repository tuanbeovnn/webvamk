package com.vamkthesis.web.service;

import com.vamkthesis.web.dto.StoreDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IStoreService {
    StoreDto save(StoreDto storeDto);

    List<StoreDto> findAll(Pageable pageable);
}
