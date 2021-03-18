package com.vamkthesis.web.api;


import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.dto.BrandDto;
import com.vamkthesis.web.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity listBrand(Pageable pageable) {
       List<BrandDto> brandDtos = brandService.findAll(pageable);
       return ResponseEntityBuilder.getBuilder().setDetails(brandDtos).build();
    }
}
