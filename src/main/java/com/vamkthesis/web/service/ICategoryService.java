package com.vamkthesis.web.service;


import com.vamkthesis.web.dto.CategoryDto;

import java.util.List;

public interface ICategoryService {
    CategoryDto save(CategoryDto categoryDto);
    void delete(long[] ids);
    List<CategoryDto> findAll();
    CategoryDto update(CategoryDto categoryInput);
}
