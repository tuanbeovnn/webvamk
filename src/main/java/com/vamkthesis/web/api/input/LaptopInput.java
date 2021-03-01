package com.vamkthesis.web.api.input;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaptopInput extends ProductInput {
    private String cpu;
    private String ram;
    private String screen;
    private String sound;
    private String os;
    private String battery;
}
