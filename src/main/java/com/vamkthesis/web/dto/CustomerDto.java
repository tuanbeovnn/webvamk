package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomerDto extends AbstractDto {
    private String customer;
    private String address;
    private String phoneNumber;
    private String email;

}
