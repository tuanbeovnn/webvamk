package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthToken {
    private String token;
    public AuthToken(String token){
        this.token = token;
    }
}
