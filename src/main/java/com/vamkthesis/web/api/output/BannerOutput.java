package com.vamkthesis.web.api.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
public class BannerOutput {
    private String content;
    private String image;
    private String url;
    private int position;

    @JsonProperty("image")
    public String[] getImage() {
        return StringUtils.isEmpty(image) ? new String[]{} : image.split(";");
    }
}
