package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.OrderDto;
import com.vamkthesis.web.entity.OrderDetailEntity;
import com.vamkthesis.web.entity.OrderEntity;
import com.vamkthesis.web.entity.ProductEntity;
import com.vamkthesis.web.repository.IOrderRepository;
import com.vamkthesis.web.repository.IProductRepository;
import com.vamkthesis.web.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private IOrderRepository orderRepository;
    @Autowired
    private IProductRepository productRepository;
    @Override
    public OrderDto save(OrderDto orderDto) {
        OrderEntity orderEntity = new OrderEntity();
        if (orderDto.getId() != null) {
            OrderEntity old = orderRepository.findById(orderDto.getId()).get();
            orderEntity = Converter.toModel(orderDto, old.getClass());
        } else {
            orderEntity = Converter.toModel(orderDto, OrderEntity.class);
        }
        orderEntity = Converter.toModel(orderDto, OrderEntity.class);
        OrderEntity finalOrderEntity = orderEntity;
        List<OrderDetailEntity> orderDetailEntity = orderDto.getOrderdetails().stream().map(e-> {
             OrderDetailEntity orderDetailEntity1 = new OrderDetailEntity();
            ProductEntity productEntity = productRepository.findById(e.getProductId()).get();
            productEntity.setQuantity(productEntity.getQuantity() - e.getQuantity());
            orderDetailEntity1.setProduct(productEntity);
            orderDetailEntity1.setQuantity(e.getQuantity());
            orderDetailEntity1.setOrder(finalOrderEntity);
            productEntity = productRepository.save(productEntity);
            return orderDetailEntity1;
        }).collect(Collectors.toList());
        orderEntity.setDetails(orderDetailEntity);
        orderEntity = orderRepository.save(orderEntity);
        OrderDto orderDto1 = Converter.toModel(orderEntity,OrderDto.class);
        return orderDto1;
    }
}
