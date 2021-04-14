package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.api.input.RatingInput;
import com.vamkthesis.web.api.output.RatingOutPut;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.entity.ProductEntity;
import com.vamkthesis.web.entity.RatingEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.paging.PageList;
import com.vamkthesis.web.repository.IProductRepository;
import com.vamkthesis.web.repository.IRatingRepository;
import com.vamkthesis.web.repository.UserRepository;
import com.vamkthesis.web.service.IRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService implements IRatingService {
    @Autowired
    private IRatingRepository ratingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;

    @Override
    public RatingEntity save(RatingInput ratingInput) {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();// lay thong tin user
        RatingEntity ratingEntity = ratingRepository.findOneUserAndProduct(myUserDTO.getId(), ratingInput.getIdProduct());
        ProductEntity productEntity = productRepository.findById(ratingInput.getIdProduct()).get();
        if (ratingEntity != null) {
            ratingEntity.setRating(ratingInput.getRating());// update rating
        } else {
            if (ratingInput.getId() != null) {
                RatingEntity old = ratingRepository.findById(ratingInput.getId()).get();
                ratingEntity = Converter.toModel(ratingInput, old.getClass());

            } else {
                ratingEntity = Converter.toModel(ratingInput, RatingEntity.class);
            }
            UserEntity userEntity = userRepository.findById(myUserDTO.getId()).get();
            ratingEntity.setUser(userEntity);
            ratingEntity.setProduct(productEntity);
        }
        ratingEntity = ratingRepository.save(ratingEntity);
        Double rating = ratingRepository.avgRating(ratingInput.getIdProduct());
        if (rating == null) {
            rating = 0D;
        }
        productEntity.setRating(rating);
        productEntity = productRepository.save(productEntity);
        return ratingEntity;
    }

    @Override
    public RatingOutPut saveNew(RatingInput ratingInput) {
        RatingEntity ratingEntity = Converter.toModel(ratingInput, RatingEntity.class);
        ProductEntity productEntity = productRepository.findById(ratingInput.getIdProduct()).get();
        ratingEntity.setProduct(productEntity);
        ratingEntity = ratingRepository.save(ratingEntity);
        Double rating = ratingRepository.avgRating(ratingInput.getIdProduct());
        if (rating == null) {
            rating = 0D;
        }
        ratingEntity.setRating(Math.ceil(rating));
        productEntity.setRating(rating);
        productEntity = productRepository.save(productEntity);
        return Converter.toModel(ratingEntity, RatingOutPut.class);
    }

    @Override
    public PageList<RatingOutPut> findAllByProductId(long id, Pageable pageable) {
        PageList<RatingOutPut> pageList = new PageList<>();
        List<RatingEntity> ratingEntity = ratingRepository.findAllByProductId(id, pageable);
        Long count = ratingRepository.countByProduct(id);
        pageList.setTotal(count);
        List<RatingOutPut> result = Converter.toList(ratingEntity, RatingOutPut.class);
        pageList.setList(result);
        pageList.setSuccess(true);
        pageList.setPageSize(pageable.getPageSize());
        pageList.setCurrentPage(pageable.getPageNumber());
        return pageList;
    }
}
