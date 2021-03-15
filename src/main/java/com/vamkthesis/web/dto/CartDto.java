package com.vamkthesis.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Setter
@Getter
public class CartDto extends AbstractDto {
//    private String cartInfo;
    private DetailsDto[] cartInfo;

//
//    @JsonProperty("cartInfo")
//    public String[] getCartInfo() {
//        return StringUtils.isEmpty(cartInfo) ? new String[]{} : cartInfo.split(";");
//    }
}
