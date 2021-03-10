package com.vamkthesis.web.api.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductInput extends AbtractInput {
    @NotNull
    protected String name;
    @NotNull
    @JsonIgnore
    protected String image;
    protected String description;
    protected String status;
    protected String categoryCode;
    protected String brandCode;
    protected String code;
    protected double rating;
    @NotNull
    protected Double price;
    protected Double originalPrice;
    protected double discount = 0;
    private String technicalInfo;

    @JsonProperty("image")
    public String[] getImage() {
        return StringUtils.isEmpty(image) ? new String[]{} : image.split(";");
    }


}
