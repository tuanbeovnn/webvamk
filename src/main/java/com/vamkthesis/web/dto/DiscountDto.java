package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Setter
@Getter
public class DiscountDto {
    private Double discount;
    private BigInteger timeEnd;
}
