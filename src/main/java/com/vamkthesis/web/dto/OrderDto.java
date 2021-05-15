package com.vamkthesis.web.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OrderDto extends AbstractDto {
    @NotEmpty(message = "Name is required")
    private String firstName;
    @NotEmpty(message = "Last Name is required")
    private String lastName;
    @NotEmpty(message = "Mobile is required")
    private String mobile;
    @NotEmpty(message = "Email is required")
    private String email;
    @NotEmpty(message = "Address is required")
    private String address;
    @NotEmpty(message = "Country is required")
    private String country;
    @NotEmpty(message = "State is required")
    private String state;
    @NotEmpty(message = "City is required")
    private String city;

    private Long zipCode;


//    private double discount;
//    private double shippingFee;
//    private double total;
//    private double tax;
    private int status;
//    private double price;
//    private double quantity;
    private List<OrderDetailsDto> details = new ArrayList<>();
}
