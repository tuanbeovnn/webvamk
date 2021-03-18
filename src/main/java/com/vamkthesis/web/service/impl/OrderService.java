package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.dto.OrderDto;
import com.vamkthesis.web.entity.OrderDetailEntity;
import com.vamkthesis.web.entity.OrderEntity;
import com.vamkthesis.web.entity.ProductEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.exception.ClientException;
import com.vamkthesis.web.repository.IOrderDetailsRepository;
import com.vamkthesis.web.repository.IOrderRepository;
import com.vamkthesis.web.repository.IProductRepository;
import com.vamkthesis.web.repository.UserRepository;
import com.vamkthesis.web.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IOrderDetailsRepository orderDetailsRepository;
    @Override
    public OrderDto save(OrderDto orderDto) {
//        OrderEntity orderEntity  = Converter.toModel(orderDto, OrderEntity.class);
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderEntity orderEntity = Converter.toModel(orderDto, OrderEntity.class);
        OrderEntity finalOrderEntity = orderEntity;
        List<OrderDetailEntity> orderDetailEntity = orderDto.getDetails().stream().map(e-> {
             OrderDetailEntity orderDetailEntity1 = new OrderDetailEntity();
            ProductEntity productEntity = productRepository.findById(e.getProductId()).get();
            if (e.getQuantity() > productEntity.getQuantity()){
                throw new ClientException("We only have " + productEntity.getQuantity());
            }
            productEntity.setQuantity(productEntity.getQuantity() - e.getQuantity());
            orderDetailEntity1.setProduct(productEntity);
            orderDetailEntity1.setQuantity(e.getQuantity());
            orderDetailEntity1.setOrder(finalOrderEntity);
            orderDetailEntity1.setImage(productEntity.getImage());
            orderDetailEntity1.setName(productEntity.getName());
            orderDetailEntity1.setPrice(productEntity.getPrice());
            orderDetailEntity1.setTotal(productEntity.getPrice() * e.getQuantity());
//            orderDetailEntity1 = orderDetailsRepository.save(orderDetailEntity1);
            productEntity = productRepository.save(productEntity);
            return orderDetailEntity1;
        }).collect(Collectors.toList());
        orderEntity.setDetails(orderDetailEntity);
        UserEntity userEntity = userRepository.findById(myUserDTO.getId()).get();
        orderEntity.setUser(userEntity);
        orderEntity = orderRepository.save(orderEntity);
        OrderDto orderDto1 = Converter.toModel(orderEntity,OrderDto.class);
        return orderDto1;
    }
}
