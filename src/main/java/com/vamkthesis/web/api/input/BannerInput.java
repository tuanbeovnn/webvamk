package com.vamkthesis.web.api.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BannerInput extends AbtractInput {
    private String content;
    private String image;
    private String url;
    private int position;
}
