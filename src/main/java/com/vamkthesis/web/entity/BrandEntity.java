package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "brands")
public class BrandEntity extends BaseEntity {

    private String name;
    private String code;
    private String image;
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<ProductEntity> products = new ArrayList<>();


}
