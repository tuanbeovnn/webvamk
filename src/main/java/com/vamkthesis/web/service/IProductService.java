package com.vamkthesis.web.service;


import com.vamkthesis.web.api.input.ProductInput;
import com.vamkthesis.web.api.input.ProductUpdateInput;
import com.vamkthesis.web.api.output.ProductOutput;
import com.vamkthesis.web.dto.DiscountDto;
import com.vamkthesis.web.paging.PageList;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductService {
    ProductOutput save(ProductInput productDto);

    void delete(long[] ids);

    void deleteById(long id);

    //    PageList<ProductOutput> findAll(Pageable pageable);// new
//    int totalItem();
    PageList<ProductOutput> findAllByCategory(String code, Pageable pageable);

    //    ProductDto findById(long id);
    PageList<ProductOutput> findAllByNewest(Pageable pageable);

    PageList<ProductOutput> searchByCategory(String name, String code, Pageable pageable);// new

    ProductOutput findProductByCode(String code);// new

    List<ProductOutput> sortByCategories(String code, Pageable pageable);// new

    PageList<ProductOutput> findProductTrending(Pageable pageable);// new

    PageList<ProductOutput> findProductBestDeal(Pageable pageable, String code);// new

    ProductOutput updateInfo(ProductUpdateInput productUpdateInput);

    PageList<ProductOutput> findAllByCategoryTrending(String code, Pageable pageable);

    DiscountDto abc();

    PageList<ProductOutput> findAllByProductQuantity(Pageable pageable);

    PageList<ProductOutput> findAllByBrands(Long id, Pageable pageable);

    PageList<ProductOutput> findAllByCategoryAndBrand(String codeCate, String codeBrand, Pageable pageable);


}
