package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MobileDto extends AbstractDto {
    private String color;
    private String screen;
    private String storage;
    private String os;
    private String weight;
    private String battery;
    private String releaseInfo;
    private String code;
}
