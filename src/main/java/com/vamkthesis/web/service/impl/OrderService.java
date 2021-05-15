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
import java.util.concurrent.atomic.AtomicReference;
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

    /**
     * @Tuan Nguyen
     * @param orderDto
     * @return
     */
    @Override
    public OrderDto save(OrderDto orderDto) {
//        OrderEntity orderEntity  = Converter.toModel(orderDto, OrderEntity.class);
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        OrderEntity orderEntity = Converter.toModel(orderDto, OrderEntity.class);
        orderEntity = orderRepository.save(orderEntity);
        OrderEntity finalOrderEntity = orderEntity;
        AtomicReference<Double> total = new AtomicReference<>(0.0);
        AtomicReference<Double> quantity = new AtomicReference<>(0.0);
//        AtomicReference<Double> price = new AtomicReference<>(0.0);

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
            ProductEntity finalProductEntity = productEntity;
            total.updateAndGet(v -> new Double((double) (v + finalProductEntity.getPrice() * e.getQuantity())));
            quantity.updateAndGet(v -> new Double((double) (v + e.getQuantity())));
//            price.updateAndGet(v -> new Double((double) (v + finalProductEntity.getPrice())));
            //orderDetailEntity1 = orderDetailsRepository.save(orderDetailEntity1);
            productEntity = productRepository.save(productEntity);
            return orderDetailEntity1;
        }).collect(Collectors.toList());
        UserEntity userEntity = userRepository.findById(myUserDTO.getId()).get();
        orderEntity.setUser(userEntity);
        orderEntity.setDetails(orderDetailEntity);
        orderEntity.setTotal(total.get());
        orderEntity.setQuantity(quantity.get());
//        orderEntity.setPrice(price.get());
        orderEntity = orderRepository.save(orderEntity);
        OrderDto orderDto1 = Converter.toModel(orderEntity,OrderDto.class);
        return orderDto1;
    }
}
