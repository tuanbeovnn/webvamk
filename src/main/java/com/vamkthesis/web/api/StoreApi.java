package com.vamkthesis.web.api;

import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.dto.StoreDto;
import com.vamkthesis.web.service.IStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/store")
public class StoreApi {
    @Autowired
    private IStoreService storeService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public StoreDto create(@RequestBody StoreDto storeDto) {
        return storeService.save(storeDto);
    }

    //    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
//    public BrandDto update(@RequestBody BrandDto brandDto, @PathVariable("id") long id) {
//        brandDto.setId(id);
//        return storeService.save(brandDto);
//    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseEntity listBrand(Pageable pageable) {
        List<StoreDto> storeDtos = storeService.findAll(pageable);
        return ResponseEntityBuilder.getBuilder().setDetails(storeDtos).build();
    }
}
