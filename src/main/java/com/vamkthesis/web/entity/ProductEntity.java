package com.vamkthesis.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "products")
public class ProductEntity extends BaseEntity {


    private String name;

    @Column(name = "image" , columnDefinition = "TEXT")
    private String image;
    private String color;
    @Column(columnDefinition = "text")
    private String description;
    private String status;
    private String code;
    private Integer quantity;

    private Double price;
    private Double originalPrice;
    private double discount = 0;
    private String technicalInfo;
    private double rating;
    private Date startTime;
    private Date endTime;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private List<RatingEntity> ratings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product")
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
