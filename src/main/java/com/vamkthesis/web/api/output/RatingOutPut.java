package com.vamkthesis.web.api.output;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RatingOutPut {
    private double rating;
    private Long idProduct;
    private String comment;
    private String email;
    private String name;
    private double ratingavg;
}
