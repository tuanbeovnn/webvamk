package com.vamkthesis.web.api;


import com.vamkthesis.web.dto.BrandDto;
import com.vamkthesis.web.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brand")
public class BrandApi {
    @Autowired
    private IBrandService brandService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public BrandDto create(@RequestBody BrandDto brandDto) {
        return brandService.save(brandDto);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public BrandDto update(@RequestBody BrandDto brandDto, @PathVariable("id") long id) {
        brandDto.setId(id);
        return brandService.save(brandDto);
    }
}
