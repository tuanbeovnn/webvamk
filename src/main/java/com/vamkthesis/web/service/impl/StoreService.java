package com.vamkthesis.web.service.impl;

import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.StoreDto;
import com.vamkthesis.web.entity.StoreEntity;
import com.vamkthesis.web.repository.IStoreRepository;
import com.vamkthesis.web.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService implements IStoreService {
    @Autowired
    private IStoreRepository storeRepository;
    @Override
    public StoreDto save(StoreDto storeDto) {
        StoreEntity entity = Converter.toModel(storeDto,StoreEntity.class);
        entity = storeRepository.save(entity);
        return Converter.toModel(entity, StoreDto.class);
    }

    @Override
    public List<StoreDto> findAll(Pageable pageable) {
        List<StoreEntity> storeEntities = storeRepository.findAll(pageable).getContent();
        List<StoreDto> storeDtos = Converter.toList(storeEntities, StoreDto.class);
        return storeDtos;
    }
}
