package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.BrandDto;
import com.vamkthesis.web.entity.BrandEntity;
import com.vamkthesis.web.exception.EmailExistsException;
import com.vamkthesis.web.repository.IBrandRepository;
import com.vamkthesis.web.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService implements IBrandService {

    @Autowired
    private IBrandRepository brandRepository;

    @Override
    public BrandDto save(BrandDto brandDto) {
        BrandEntity brandEntity = new BrandEntity();
        brandEntity = Converter.toModel(brandDto, BrandEntity.class);
        BrandEntity brandEntity1 = brandRepository.findOneByCode(brandDto.getCode());
        if (brandEntity1 != null) {
            throw new EmailExistsException("Brand Code already exits");
        }
        brandEntity = brandRepository.save(brandEntity);
        return Converter.toModel(brandEntity, BrandDto.class);
    }

    @Override
    public List<BrandDto> findAll(Pageable pageable) {
        List<BrandEntity> brandEntity = brandRepository.findAll(pageable).getContent();
        List<BrandDto> brandDto = Converter.toList(brandEntity, BrandDto.class);
        return brandDto;
    }

    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    @Override
    public BrandDto update(BrandDto brandDto) {
        BrandEntity brandEntity = brandRepository.findById(brandDto.getId()).get();
        brandEntity.setCode(brandDto.getCode());
        brandEntity.setImage(brandDto.getImage());
        brandEntity.setName(brandDto.getName());
        brandEntity = brandRepository.save(brandEntity);
        BrandDto brandDto1 = Converter.toModel(brandEntity, BrandDto.class);
        return brandDto1;
    }
}
