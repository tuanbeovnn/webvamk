package com.vamkthesis.web.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "banners")
public class BannerEntity extends BaseEntity {

    private String content;
    private String image;
    private String url;
    private int position;

//    @JsonIgnore
//    @OneToMany(mappedBy = "banner", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<ProductEntity> products = new ArrayList<>();

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
