package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDto extends AbstractDto {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
}
