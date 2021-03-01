package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.BrandDto;
import com.vamkthesis.web.entity.BrandEntity;
import com.vamkthesis.web.exception.EmailExistsException;
import com.vamkthesis.web.repository.IBrandRepository;
import com.vamkthesis.web.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BrandService implements IBrandService {

    @Autowired
    private IBrandRepository brandRepository;
    
    @Override
    public BrandDto save(BrandDto brandDto) {
        BrandEntity brandEntity = new BrandEntity();
        if (brandDto.getId() != null){
            BrandEntity oldBrand = brandRepository.findById(brandDto.getId()).get();
            brandEntity = Converter.toModel(brandDto, oldBrand.getClass());
        }else {
            brandEntity = Converter.toModel(brandDto,BrandEntity.class);
            BrandEntity brandEntity1 = brandRepository.findOneByCode(brandDto.getCode());
            if (brandEntity1 != null){
                throw new EmailExistsException("Brand Code already exits");
            }
        }
        brandEntity = brandRepository.save(brandEntity);
        return Converter.toModel(brandEntity, BrandDto.class);
    }
}
