package com.vamkthesis.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class ProductDto extends AbstractDto<ProductDto> {

    private String name;
//    private String origin;
//    private String warranty;
    private String[] image;
    private String description;
    private String status;
    private Double price;
    @JsonIgnore
    private String categoryCode;
    @JsonIgnore
    private String brandCode;
    private String code;



}
