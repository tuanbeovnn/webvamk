package com.vamkthesis.web.service;


import com.vamkthesis.web.api.input.RatingInput;
import com.vamkthesis.web.api.output.RatingOutPut;
import com.vamkthesis.web.entity.RatingEntity;
import com.vamkthesis.web.paging.PageList;
import org.springframework.data.domain.Pageable;

public interface IRatingService {
    RatingEntity save(RatingInput ratingInput);
    RatingOutPut saveNew(RatingInput ratingInput);
    PageList<RatingOutPut> findAllByProductId(long id, Pageable pageable);

}
