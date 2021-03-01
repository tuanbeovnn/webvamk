package com.vamkthesis.web.service;

import com.vamkthesis.web.api.input.BannerInput;



public interface IBannerService {
    BannerInput save(BannerInput input);

    void delete(Long id);
}
