package com.vamkthesis.web.api;


import com.vamkthesis.web.dto.OrderDto;
import com.vamkthesis.web.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderApi {
    @Autowired
    private IOrderService orderService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public OrderDto save(@RequestBody @Valid OrderDto orderDto) {
        return orderService.save(orderDto);
    }
}
