package com.vamkthesis.web.api;


import com.vamkthesis.web.api.input.ProductInput;
import com.vamkthesis.web.api.input.RatingInput;
import com.vamkthesis.web.api.output.ProductOutput;
import com.vamkthesis.web.entity.ProductEntity;
import com.vamkthesis.web.entity.RatingEntity;
import com.vamkthesis.web.paging.PageList;
import com.vamkthesis.web.service.IProductService;
import com.vamkthesis.web.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductApi {

    @Autowired
    private IProductService productService;

    @Autowired
    private IRatingService ratingService;


    @PutMapping(value = "/{id}")
    public ProductEntity updateProduct(@RequestBody ProductInput productDto, @PathVariable("id") long id) {
        productDto.setId(id);
        return productService.save(productDto);
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ProductOutput findProductByCodeDetails(@PathVariable("code") String code) {
        return productService.findProductByCode(code);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PageList<ProductOutput> findProductByName(String name, String code, Pageable pageable) {
        PageList<ProductOutput> pageList = productService.searchByCategory(name, code, pageable);
        return pageList;
    }

    @RequestMapping(value = "/sortBy", method = RequestMethod.GET)
    public List<ProductOutput> sortProduct(String code, Pageable pageable) {
        List<ProductOutput> productOutputs = productService.sortByCategories(code, pageable);
        return productOutputs;
    }

    @RequestMapping(value = "/rating", method = RequestMethod.POST)
    public RatingEntity rating(@ModelAttribute RatingInput ratingInput) {
        return ratingService.save(ratingInput);
    }

    @DeleteMapping(value = "/remove-list")
    public void deleteProduct(@RequestBody long[] ids) {
        productService.delete(ids);
    }

    @Secured("ROLE_ADMIN")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ProductEntity save(@RequestBody ProductInput productInput){
        return productService.save(productInput);
    }



//    @RequestMapping(value = "/product-category", method = RequestMethod.GET)
//    public List<ProductDto> showListProductCategory(@ModelAttribute ProductDto productDto, Pageable pageable) {
//        return productService.findAllByCategory(productDto.getCategoryCode(), pageable);
//    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public PageList<ProductInput> showListProductCategory(Pageable pageable) {
        PageList<ProductInput> pageList = productService.findAllByNewest(pageable);
        return pageList;
    }

    @RequestMapping(value = "/bestdeal", method = RequestMethod.GET)
    public PageList<ProductInput> showListProductBestDeal(Pageable pageable, String code) {
        PageList<ProductInput> pageList = productService.findProductBestDeal(pageable,code);
        return pageList;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public PageList<ProductOutput> listProduct(String code, Pageable pageable) {
        PageList<ProductOutput> pageList = productService.findAllByCategory(code,pageable);
        return pageList;
    }

    @RequestMapping(value = "/trending", method = RequestMethod.GET)
    public PageList<ProductOutput> listTrending(Pageable pageable) {
        PageList<ProductOutput> outputPageList = productService.findProductTrending(pageable);
        return outputPageList;
    }

//    @RequestMapping(value = "/searchByCategory", method = RequestMethod.GET)
//    public List<ProductInput> listSearch(@RequestBody ProductInput input) {
//        return productService.searchByCategory(input.getCategoryCode(),input.getName());
//    }
}
