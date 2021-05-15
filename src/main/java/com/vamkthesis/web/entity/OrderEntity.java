package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
@ToString
public class OrderEntity extends BaseEntity {

    private String firstName;
    private String lastName;
    private String mobile;
    private String email;
    private String address;
    private String country;
    private String state;
    private String city;
    private Long zipCode;
    private double discount;
    private double shippingFee;
    private double total;
    private double tax;
    private int status;
    private double price;
    private double quantity;
    private String codeOrder;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

//    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
//    private List<TransactionEntity> trans = new ArrayList<>();

    @OneToMany(mappedBy = "order", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderDetailEntity> details = new ArrayList<>();


}
