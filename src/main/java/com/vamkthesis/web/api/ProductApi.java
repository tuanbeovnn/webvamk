package com.vamkthesis.web.api;


import com.vamkthesis.web.api.input.ProductInput;
import com.vamkthesis.web.api.input.ProductUpdateInput;
import com.vamkthesis.web.api.input.RatingInput;
import com.vamkthesis.web.api.output.ProductOutput;
import com.vamkthesis.web.api.output.RatingOutPut;
import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.dto.DiscountDto;
import com.vamkthesis.web.paging.PageList;
import com.vamkthesis.web.service.IProductService;
import com.vamkthesis.web.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/api/product")
public class ProductApi {

    @Autowired
    private IProductService productService;

    @Autowired
    private IRatingService ratingService;

    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateProduct(@RequestBody ProductUpdateInput productUpdateInput, @PathVariable("id") long id) {
        productUpdateInput.setId(id);
        ProductOutput productOutput = productService.updateInfo(productUpdateInput);
        return ResponseEntityBuilder.getBuilder().setMessage("Update product successfully").setDetails(productOutput).build();
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ProductOutput findProductByCodeDetails(@PathVariable("code") String code) {
        return productService.findProductByCode(code);
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public PageList<ProductOutput> findProductByName(@RequestParam String name, String code, Pageable pageable) {
        PageList<ProductOutput> pageList = productService.searchByCategory(name, code, pageable);
        return pageList;
    }

    @RequestMapping(value = "/sortBy", method = RequestMethod.GET)
    public List<ProductOutput> sortProduct(String code, Pageable pageable) {
        List<ProductOutput> productOutputs = productService.sortByCategories(code, pageable);
        return productOutputs;
    }

    @RequestMapping(value = "/rating", method = RequestMethod.POST)
    public ResponseEntity rating(@RequestBody RatingInput ratingInput) {
        RatingOutPut ratingOutPut = ratingService.saveNew(ratingInput);
        return ResponseEntityBuilder.getBuilder().setDetails(ratingOutPut).setMessage("Save rating successfully").build();
    }
    @RequestMapping(value = "/listRating", method = RequestMethod.GET)
    public PageList<RatingOutPut> showListProductRating(@RequestParam long id, Pageable pageable) {
        PageList<RatingOutPut> pageList = ratingService.findAllByProductId(id,pageable);
        return pageList;
    }

    @DeleteMapping(value = "/remove-list")
    public void deleteProduct(@RequestBody long[] ids) {
        productService.delete(ids);
    }

    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/remove/{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") long id) {
        productService.deleteById(id);
        return ResponseEntityBuilder.getBuilder().setMessage("Delete product successfully").build();
    }

    @PreAuthorize("hasRole('ROLE_STAFF') or hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody @Valid ProductInput productInput){
        ProductOutput productOutput = productService.save(productInput);
        return ResponseEntityBuilder.getBuilder().setDetails(productOutput).setMessage("Save product successfully").build();

    }



    @RequestMapping(value = "/trendingCate", method = RequestMethod.GET)
    public PageList<ProductOutput> showListProductCategoryTredingCategory(String code, Pageable pageable) {
        PageList<ProductOutput> pageList = productService.findAllByCategoryTrending(code,pageable);
        return pageList;
    }



    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public PageList<ProductOutput> showListProductCategory(Pageable pageable) {
        PageList<ProductOutput> pageList = productService.findAllByNewest(pageable);
        return pageList;
    }

    @RequestMapping(value = "/bestdeal", method = RequestMethod.GET)
    public PageList<ProductOutput> showListProductBestDeal(Pageable pageable, String code) {
        PageList<ProductOutput> pageList = productService.findProductBestDeal(pageable,code);
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

    @RequestMapping(value = "/bigdeal", method = RequestMethod.GET)
    public DiscountDto showListBigDeal() {
        DiscountDto pageList = productService.abc();
        return pageList;
    }



//    @RequestMapping(value = "/searchByCategory", method = RequestMethod.GET)
//    public List<ProductInput> listSearch(@RequestBody ProductInput input) {
//        return productService.searchByCategory(input.getCategoryCode(),input.getName());
//    }
}
