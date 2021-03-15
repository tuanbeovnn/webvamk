package com.vamkthesis.web.dto;


import com.vamkthesis.web.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderDetailsDto extends BaseEntity {
    private Long productId;
    private Integer quantity;
    private String name;
    private double total;
    private double price;

//    private double shippingFee;

}
