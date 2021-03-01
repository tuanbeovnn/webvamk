package com.vamkthesis.web.api.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ProductInput extends AbtractInput {
    @NotNull
    protected String name;
    @NotNull
    protected String[] image;
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


}
