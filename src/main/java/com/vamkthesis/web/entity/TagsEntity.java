package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "tags")
public class TagsEntity extends BaseEntity{
    private String name;

//
//    @ManyToMany(mappedBy = "products")
//    private List<ProductEntity> products = new ArrayList<>();
}
