package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
@Table(name = "categories")
public class CategoryEntity extends BaseEntity {
    private String name;
    private String code;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ProductEntity> products = new ArrayList<>();

//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<ArticlesEntity> articles = new ArrayList<>();



}
