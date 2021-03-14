package com.vamkthesis.web.service;


import com.vamkthesis.web.api.output.CartOutput;
import com.vamkthesis.web.dto.CartDto;

import java.util.List;

public interface ICartService {
    CartDto save(CartOutput cartOutput);
    void deleteById(Long id);
    List<CartDto> findAll();
    List<CartDto> findAllCartByUser(Long id);
}
