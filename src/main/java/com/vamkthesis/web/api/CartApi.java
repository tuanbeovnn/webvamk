//package com.vamkthesis.web.api;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.vamkthesis.web.dto.CartDto;
//import com.vamkthesis.web.dto.DetailsDto;
//import com.vamkthesis.web.service.ICartService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/cart")
//public class CartApi {
//    @Autowired
//    private ICartService cartService;
//
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public CartDto createCart(@RequestBody CartDto cartDto) throws JsonProcessingException {
//        return cartService.save(cartDto);
//    }
//
//    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
//    public CartDto showListCart(@PathVariable Long id) throws JsonProcessingException {
//        return cartService.findAllCartByUser(id);
//    }
//}
