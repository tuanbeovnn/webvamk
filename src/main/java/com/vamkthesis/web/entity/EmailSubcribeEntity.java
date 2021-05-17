package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "emailsubcribe")
public class EmailSubcribeEntity extends BaseEntity {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String message;
}
