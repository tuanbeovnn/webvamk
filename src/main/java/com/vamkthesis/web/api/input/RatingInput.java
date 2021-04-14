package com.vamkthesis.web.api.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class  RatingInput extends AbtractInput {
    private double rating;
    private Long idProduct;
    private String comment;
    private String email;
    private String name;
}
