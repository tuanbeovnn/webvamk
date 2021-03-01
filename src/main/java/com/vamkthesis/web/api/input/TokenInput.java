package com.vamkthesis.web.api.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class TokenInput {
    @NotNull
    @Size(min = 10)
    private String accessToken;
}
