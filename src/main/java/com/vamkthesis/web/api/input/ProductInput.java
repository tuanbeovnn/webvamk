package com.vamkthesis.web.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductInput extends AbtractInput {
    @NotEmpty(message = "Name is required")
    protected String name;
    @NotEmpty(message = "Image is required")
    protected String[] image;
    protected String[] color;
    @NotEmpty(message = "Description is required")
    protected String description;
    protected String status;
    @NotEmpty(message = "Category Code is required")
    protected String categoryCode;
    @NotEmpty(message = "Brand Code is required")
    protected String brandCode;
    protected String code;
    @NotNull(message = "Quantity is required")
    private Integer quantity;
    @NotNull(message = "Original Price is required")
    protected Double originalPrice;
    protected double discount = 0;
    private String technicalInfo;


}
