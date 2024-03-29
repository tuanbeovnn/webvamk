package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.api.input.ProductInput;
import com.vamkthesis.web.api.output.ProductOutput;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.entity.BrandEntity;
import com.vamkthesis.web.entity.CategoryEntity;
import com.vamkthesis.web.entity.ProductEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.exception.ClientException;
import com.vamkthesis.web.paging.PageList;
import com.vamkthesis.web.repository.*;
import com.vamkthesis.web.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService implements IProductService {

    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ICategoryRepository iCategoryRepository;
    @Autowired
    private IBrandRepository brandRepository;
    @Autowired
    private IRatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
//    @Autowired
//    private IMobileRepository mobileRepository;


    @Secured("ADMIN")
    @Override
    public ProductEntity save(ProductInput productInput) {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ProductEntity productEntity = new ProductEntity();
            productEntity = Converter.toModel(productInput, ProductEntity.class);
//            ProductEntity inforDetails = productEntity;
//            List<MobileEntity> mobileEntities = productInput.getMobileDetails().stream().map(e -> {
//                MobileEntity mobileEntity = Converter.toModel(e, MobileEntity.class);
//                mobileEntity.setProduct(inforDetails);
//                return mobileEntity;
//            }).collect(Collectors.toList());
//            productEntity.setMobiles(mobileEntities);
//        MobileEntity mobileEntity = mobileRepository.findOneByCode(productInput.getProductCode());
//        productEntity.setMobile(mobileEntity);
        CategoryEntity categoryEntity = iCategoryRepository.findOneByCode(productInput.getCategoryCode());
        productEntity.setCategory(categoryEntity);
        BrandEntity brandEntity = brandRepository.findOneByCode(productInput.getBrandCode());
        productEntity.setBrand(brandEntity);
        productEntity.setPrice((productInput.getOriginalPrice() * (100 - productInput.getDiscount()) / 100));
//        productEntity.setOriginalPrice(100 - (productEntity.getPrice() * 100) / productEntity.getOriginalPrice());
        String image = String.join(";", productInput.getImage());
        productEntity.setImage(image);
        UserEntity userEntity = userRepository.findById(myUserDTO.getId()).get();
        productEntity.setUser(userEntity);
        productEntity = productRepository.save(productEntity);
        return productEntity;
    }

    @Override
    public void delete(long[] ids) {
        for (long item : ids) {
            productRepository.deleteById(item);
        }
    }

    @Override
    public PageList<ProductOutput> findAllByCategory(String code, Pageable pageable) {
        List<ProductEntity> productEntities = new ArrayList<>();
        if (code == null){
            productEntities = productRepository.findAllByProduct(pageable);
        }else {
            productEntities = productRepository.findAllByCategory(code, pageable);
            if (productEntities == null) {
                throw new ClientException(HttpStatus.NOT_FOUND, "Not Found");
            }
        }
        Long count = productRepository.countByProduct();
        List<ProductOutput> results = Converter.toList(productEntities, ProductOutput.class);
        PageList<ProductOutput> pageList = new PageList<>();
        pageList.setList(results);
        pageList.setSuccess(true);
        pageList.setTotal(count);
        pageList.setPageSize(pageable.getPageSize());
        pageList.setCurrentPage(pageable.getPageNumber());
        return pageList;
    }


    @Override
    public PageList<ProductOutput> searchByCategory(String name, String code, Pageable pageable) {
        List<ProductEntity> productEntity = new ArrayList<>();
        if (code == null){
            productEntity = productRepository.searchProductByName(name, pageable);
            if (productEntity == null) {
                throw new ClientException(HttpStatus.NOT_FOUND, "Not Found");
            }
        }else {
            productEntity = productRepository.searchProductByCategory(name, code, pageable);
            if (productEntity == null) {
                throw new ClientException(HttpStatus.NOT_FOUND, "Not Found");
            }
        }
        Long count = productRepository.countByProduct();
        List<ProductOutput> results = Converter.toList(productEntity, ProductOutput.class);
        PageList<ProductOutput> pageList = new PageList<>();
        pageList.setList(results);
        pageList.setSuccess(true);
        pageList.setTotal(count);
        pageList.setPageSize(pageable.getPageSize());
        pageList.setCurrentPage(pageable.getPageNumber());
        return pageList;
    }

//    @Override
//    public int totalItem() {
//        return (int) ProductRepository.count();
//    }

//    @Override
//    public List<ProductDto> findAll(ProductDto dto, Pageable pageable) {
////        Map<String, Object> properties = converToMapProperties(fieldSearch);
//        ProductSearchBuilder builder = Converter.toModel(dto, ProductSearchBuilder.Builder.class).build();
//        List<ProductEntity> results = productRepository.findAll(builder,pageable);
//        return results.stream().map(item -> {
//            ProductDto temp = (Converter.toModel(item, ProductDto.class));
//            return temp;
//        }).collect(Collectors.toList());
//    }


//    @Override
//    public void delete(long id) {
//        ProductRepository.deleteById(id);
//    }

//    @Override
//    public ProductDto findById(long id) {
//        ProductEntity entity = productRepository.findById(id).get();
//        ProductDto dto = Converter.toModel(entity,ProductDto.class);
//        return dto;
//    }

    @Override
    public PageList<ProductInput> findAllByNewest(Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.findAllByNewest(pageable);
        Long count = productRepository.countByProduct();
        List<ProductInput> results = Converter.toList(productEntities, ProductInput.class);
        PageList<ProductInput> pageList = new PageList<>();
        pageList.setList(results);
        pageList.setSuccess(true);
        pageList.setTotal(count);
        pageList.setPageSize(pageable.getPageSize());
        pageList.setCurrentPage(pageable.getPageNumber());
        return pageList;
//        return productEntities.stream().map(e -> Converter.toModel(e, ProductInput.class)).collect(Collectors.toList());
    }


    @Override
    public ProductOutput findProductByCode(String code) {
        ProductEntity productEntity = productRepository.findProductByCode(code);
        if (productEntity == null) {
            throw new ClientException(HttpStatus.NOT_FOUND, "Not Found");
        }
        ProductOutput productOutput = Converter.toModel(productEntity, ProductOutput.class);
        List<ProductEntity> productEntities = new ArrayList<>();
        Pageable pageable = PageRequest.of(0,10);
        productEntities = productRepository.findRelatedProductByCode(code, pageable);
        Long count = productRepository.countByProduct();
        List<ProductOutput> results = Converter.toList(productEntities, ProductOutput.class);
        productOutput.setRelatedProduct(results);
        return productOutput;
    }

    @Override
    public List<ProductOutput> sortByCategories(String code, Pageable pageable) {
        List<ProductEntity> productEntities = productRepository.sortByCategories(code, pageable);
        List<ProductOutput> productOutput = Converter.toList(productEntities,ProductOutput.class);
        return productOutput;
    }

    @Override
    public PageList<ProductOutput> findProductTrending(Pageable pageable) {
        List<ProductEntity> productEntity = productRepository.findAllByTrendding(pageable);
        Long count = productRepository.countByProduct();
        List<ProductOutput> results = Converter.toList(productEntity, ProductOutput.class);
        PageList<ProductOutput> pageList = new PageList<>();
        pageList.setList(results);
        pageList.setSuccess(true);
        pageList.setTotal(count);
        pageList.setPageSize(pageable.getPageSize());
        pageList.setCurrentPage(pageable.getPageNumber());
        return pageList;
    }

    @Override
    public PageList<ProductInput> findProductBestDeal(Pageable pageable, String code) {
        List<ProductEntity> productEntities = new ArrayList<>();
        if (code == null) {
            productEntities = productRepository.findAllByBestDeal(pageable);
            if (productEntities == null) {
                throw new ClientException(HttpStatus.NOT_FOUND, "Not Found");
            }
        }else {
            productEntities = productRepository.findAllByCodeBestDeal(pageable,code);
            if (productEntities == null) {
                throw new ClientException(HttpStatus.NOT_FOUND, "Not Found");
            }
        }
        List<ProductInput> results = Converter.toList(productEntities, ProductInput.class);
        PageList<ProductInput> pageList = new PageList<>();
        pageList.setList(results);
        pageList.setSuccess(true);
        pageList.setPageSize(pageable.getPageSize());
        pageList.setCurrentPage(pageable.getPageNumber());
        return pageList;
    }


}
