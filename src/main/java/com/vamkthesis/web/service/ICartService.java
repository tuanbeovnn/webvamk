package com.vamkthesis.web.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.vamkthesis.web.dto.CartDto;

import java.util.List;

public interface ICartService {
    CartDto save(CartDto cartDto) throws JsonProcessingException;
    void deleteById(Long id);
    List<CartDto> findAll();
    CartDto findAllCartByUser(Long id) throws JsonProcessingException;
}
