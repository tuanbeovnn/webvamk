package com.vamkthesis.web.api.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ProductOutput {
    private Long id;
    private double rating;
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
    @NotNull
    protected Double price;
    protected Double originalPrice;
    protected double discount = 0;
    private String technicalInfo;
    private Long timeEnd;
    private Integer quantity;
    private List<ProductOutput> relatedProduct = new ArrayList<>();

    @JsonProperty("image")
    public String[] getImage() {
        return StringUtils.isEmpty(image) ? new String[]{} : image.split(";");
    }

}
