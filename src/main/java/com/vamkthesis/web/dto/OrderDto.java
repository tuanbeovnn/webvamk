package com.vamkthesis.web.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OrderDto extends AbstractDto {
    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String address;
    private String country;
    private String city;
    private double discount;
    private double shippingFee;
    private double total;
    private double tax;
    private int status;
    private double price;
    private List<OrderDetailsDto> orderdetails = new ArrayList<>();
}
