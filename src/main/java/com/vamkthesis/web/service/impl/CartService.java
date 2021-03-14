package com.vamkthesis.web.service.impl;

import com.vamkthesis.web.api.output.CartOutput;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.CartDto;
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
    public CartDto save(CartOutput cartDto) {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();// lay thong tin user
        UserEntity userEntity = userRepository.findById(myUserDTO.getId()).get();
        CartEntity cartEntity = Converter.toModel(cartDto,CartEntity.class);
        cartEntity.setUser(userEntity);
//        String cartInfo = String.join(";", cartDto.getCartInfo());
//        cartEntity.setCartInfo(cartInfo);
//        cartEntity = cartRepository.save(cartEntity);
        return Converter.toModel(cartEntity, CartDto.class);
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<CartDto> findAll() {
        return null;
    }

    @Override
    public List<CartDto> findAllCartByUser(Long id) {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<CartEntity> cartEntity = cartRepository.findALlCartByUser(myUserDTO.getId());
        List<CartDto> cartDtos = Converter.toList(cartEntity,CartDto.class);
        return cartDtos;
    }
}
