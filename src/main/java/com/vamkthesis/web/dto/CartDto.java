package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class CartDto extends AbstractDto {
//    private String cartInfo;
    private List<DetailsDto> cartInfo = new ArrayList<>();


//    @JsonProperty("cartInfo")
//    public String[] getCartInfo() {
//        return StringUtils.isEmpty(cartInfo) ? new String[]{} : cartInfo.split(";");
//    }
}
