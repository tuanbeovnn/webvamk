package com.vamkthesis.web.api;


import com.vamkthesis.web.dto.CategoryDto;
import com.vamkthesis.web.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/category")
public class CategoryApi {
    @Autowired
    private ICategoryService categoryService;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public CategoryDto createCategory(@RequestBody CategoryDto CategoryDto) {
        return categoryService.save(CategoryDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public CategoryDto updateCate(@RequestBody CategoryDto categoryDto, @PathVariable("id") long id) {
        categoryDto.setId(id);
        return categoryService.update(categoryDto);
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CategoryDto> showListCategory(@ModelAttribute CategoryDto categoryDto){
        return categoryService.findAll();
    }

    @RequestMapping(value = "/remove", method = RequestMethod.DELETE)
    public void remove(@RequestBody long[] ids) {
        categoryService.delete(ids);
    }

}
