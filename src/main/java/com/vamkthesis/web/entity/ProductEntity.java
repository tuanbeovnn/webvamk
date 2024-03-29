package com.vamkthesis.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class ProductEntity extends BaseEntity {

    @NotNull
    private String name;
    @NotNull
    private String image;
    private String description;
    private String status;
    private String brandCode;
    private String code;
    private Integer quantity;
    @NotNull
    private Double price;
    private Double originalPrice;
    private double discount = 0;
    private String technicalInfo;
    private double rating;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<RatingEntity> ratings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderDetailEntity> details = new ArrayList<>();

//    @JsonIgnore
//    @LazyCollection(LazyCollectionOption.FALSE)
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
//    private List<T> details = new ArrayList<>();


//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "mobile_id")
//    private MobileEntity mobile;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

//    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "banner_id")
//    private BannerEntity banner;

//    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinTable(name = "product_tag",
//            joinColumns = @JoinColumn(name = "product_id"),
//            inverseJoinColumns = @JoinColumn(name = "tag_id"))
//    private List<TagsEntity> tags = new ArrayList<>();



}
