package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "roles")
public class RoleEntity extends BaseEntity {

    private String name;
    private String discription;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users = new ArrayList<>();

    @Override
    public String toString() {// convert list roles in entity
        return name;
    }
}
