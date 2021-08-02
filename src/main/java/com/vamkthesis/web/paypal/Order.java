package com.vamkthesis.web.paypal;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Order {
    private Long id;
    private String email;
    private double price;
    private String currency;
    private String method;
    private String intent;
    private String description;

}
