package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class LaptopEntity {

    private String cpu;
    private String ram;
    private String screen;
    private String sound;
    private String os;
    private String battery;

}
