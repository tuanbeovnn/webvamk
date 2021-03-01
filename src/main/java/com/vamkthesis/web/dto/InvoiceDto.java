package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InvoiceDto extends AbstractDto {

    private double totalPrice;
    private String deliveryTo;
    private String note;

}
