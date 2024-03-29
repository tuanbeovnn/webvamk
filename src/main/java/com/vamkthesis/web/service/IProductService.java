package com.vamkthesis.web.service;


import com.vamkthesis.web.api.input.ProductInput;
import com.vamkthesis.web.api.output.ProductOutput;
import com.vamkthesis.web.entity.ProductEntity;
import com.vamkthesis.web.paging.PageList;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    ProductEntity save(ProductInput productDto);
    void delete(long[] ids);
//    PageList<ProductOutput> findAll(Pageable pageable);// new
//    int totalItem();
    PageList<ProductOutput> findAllByCategory(String code, Pageable pageable);
//    ProductDto findById(long id);
    PageList<ProductInput> findAllByNewest(Pageable pageable);
    PageList<ProductOutput> searchByCategory(String name, String code, Pageable pageable);// new
    ProductOutput findProductByCode(String code);// new
    List<ProductOutput> sortByCategories(String code, Pageable pageable);// new
    PageList<ProductOutput> findProductTrending(Pageable pageable);// new
    PageList<ProductInput> findProductBestDeal(Pageable pageable, String code);// new


}
