package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DetailInvoiceDto extends AbstractDto {
    private long quantity;
    private long unitPrice;


}
