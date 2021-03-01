package com.vamkthesis.web.api.input;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MobileInput extends ProductInput {
    private String color;
    private String screen;
    private String storage;
    private String os;
    private String weight;
    private String battery;
    private String releaseInfo;

//    private List<MobileInput> mobileDetails;


}
