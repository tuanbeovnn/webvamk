package com.vamkthesis.web.api.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartOutput {
    private String name;
    private double price;
    private String image;
    private String qty;
}
