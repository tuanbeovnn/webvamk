package com.vamkthesis.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class MobileEntity {

    private String color;
    private String screen;
    private String storage;
    private String os;
    private String weight;
    private String battery;
    private String releaseInfo;

//    @OneToMany(mappedBy = "mobile", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<ProductEntity> products = new ArrayList<>();
//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
//    @JoinColumn(name = "product_id")
//    private ProductEntity product;

}
