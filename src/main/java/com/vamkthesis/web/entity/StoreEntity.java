package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "stores")
public class StoreEntity extends BaseEntity {
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
}
