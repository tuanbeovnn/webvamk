package com.vamkthesis.web.service;

import com.vamkthesis.web.api.input.BannerInput;
import com.vamkthesis.web.paging.PageList;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface IBannerService {
    BannerInput save(BannerInput input);
    void delete(Long id);
    List<BannerInput> findAllByPosition(int position);
    PageList<BannerInput> findAll(Pageable pageable);
}
