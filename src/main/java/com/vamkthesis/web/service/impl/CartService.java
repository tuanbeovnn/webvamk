package com.vamkthesis.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vamkthesis.web.api.output.CartOutput;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.CartDto;
import com.vamkthesis.web.dto.DetailsDto;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.entity.CartEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.repository.ICartRepository;
import com.vamkthesis.web.repository.UserRepository;
import com.vamkthesis.web.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService implements ICartService {
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public CartDto save(CartDto cartDto) throws JsonProcessingException {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();// lay thong tin user
        UserEntity userEntity = userRepository.findById(myUserDTO.getId()).get();
        CartEntity cartEntity = Converter.toModel(cartDto,CartEntity.class);
        cartEntity.setUser(userEntity);
        String cartInfo = new ObjectMapper().writeValueAsString(cartDto.getCartInfo());
        cartEntity.setCartInfo(cartInfo);
        cartEntity = cartRepository.save(cartEntity);
        cartDto = Converter.toModel(cartEntity, CartDto.class);
        DetailsDto[] dtos = new ObjectMapper().readValue(cartEntity.getCartInfo(),DetailsDto[].class);
        cartDto.setCartInfo(dtos);
        return cartDto;
    }
//[{"name":"MAcbook Pro 13", "price":"1", "qty":"10","image":"url"},{"name":"MAcbook Pro 13", "price":"1", "qty":"10","image":"url"}]
    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<CartDto> findAll() {
        return null;
    }

    @Override
    public CartDto findAllCartByUser(Long id) throws JsonProcessingException {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CartEntity cartEntity = cartRepository.findALlCartByUser(myUserDTO.getId());
        CartDto cartDtos = Converter.toModel(cartEntity,CartDto.class);
        DetailsDto[] dtos = new ObjectMapper().readValue(cartEntity.getCartInfo(),DetailsDto[].class);
        cartDtos.setCartInfo(dtos);
        return cartDtos;
    }
}
