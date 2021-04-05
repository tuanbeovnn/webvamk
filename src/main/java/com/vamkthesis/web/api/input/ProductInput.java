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
    @NotEmpty(message = "Description is required")
    protected String description;
    protected String status;
    @NotEmpty(message = "Category Code is required")
    protected String categoryCode;
    @NotEmpty(message = "Brand Code is required")
    protected String brandCode;
    protected String code;
    protected double rating;
    @NotNull(message = "Quantity is required")
    private Integer quantity;
    protected Double price;
    @NotNull(message = "Original Price is required")
    protected Double originalPrice;
    protected double discount = 0;
    private String technicalInfo;

//    @JsonProperty("image")
//    public String[] getImage() {
//        return StringUtils.isEmpty(image) ? new String[]{} : image.split(";");
//    }


}
