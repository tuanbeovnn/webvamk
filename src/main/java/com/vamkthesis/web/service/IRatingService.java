package com.vamkthesis.web.service;


import com.vamkthesis.web.api.input.RatingInput;
import com.vamkthesis.web.entity.RatingEntity;

public interface IRatingService {
    RatingEntity save(RatingInput ratingInput);

}
