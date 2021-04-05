package com.vamkthesis.web.api.input;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductUpdateInput extends AbtractInput {

    private String name;
    protected String[] image;
    private String description;
    private String status;
    private String code;
    private Integer quantity;
    private Double price;
    private Double originalPrice;
    private double discount = 0;
    private String technicalInfo;
    private double rating;
    private Date endTime;


//    @JsonProperty("image")
//    public String[] getImage() {
//        return StringUtils.isEmpty(image) ? new String[]{} : image.split(";");
//    }
}
