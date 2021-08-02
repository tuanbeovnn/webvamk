package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.CategoryDto;
import com.vamkthesis.web.entity.CategoryEntity;
import com.vamkthesis.web.exception.EmailExistsException;
import com.vamkthesis.web.repository.ICategoryRepository;
import com.vamkthesis.web.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    ICategoryRepository categoryRepository;

    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    @Override
    public CategoryDto save(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = new CategoryEntity();
        if (categoryDto.getId() != null) {
            CategoryEntity oldCategory = categoryRepository.findById(categoryDto.getId()).get();
            categoryEntity = Converter.toModel(categoryDto, oldCategory.getClass());
        } else {
            categoryEntity = Converter.toModel(categoryDto, CategoryEntity.class);
            CategoryEntity categoryEntity1 = categoryRepository.findOneByCode(categoryDto.getCode());
            if (categoryEntity1 != null) {
                throw new EmailExistsException("Category code has been already exists.");
            }
        }
        categoryEntity = categoryRepository.save(categoryEntity);
        return Converter.toModel(categoryEntity, CategoryDto.class);
    }

    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    @Override
    public void delete(long[] ids) {
        for (long item : ids) {
            categoryRepository.deleteById(item);
        }
    }

    @Override
    public List<CategoryDto> findAll() {
        List<CategoryEntity> categoryEntity = categoryRepository.findAll();
        return categoryEntity.stream().map(e -> Converter.toModel(e, CategoryDto.class)).collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    @Override
    public CategoryDto update(CategoryDto categoryDto) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryDto.getId()).get();
        categoryEntity.setName(categoryDto.getName());
        categoryEntity.setCode(categoryDto.getCode());
        categoryEntity = categoryRepository.save(categoryEntity);
        CategoryDto categoryDto1 = Converter.toModel(categoryEntity, CategoryDto.class);
        return categoryDto1;
    }
}
