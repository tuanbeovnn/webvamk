package com.vamkthesis.web.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column
    @CreatedBy
    private String createdBy;


    @Column
    @CreatedDate
    @JsonIgnore
    private Date createdDate;

    @JsonIgnore
    @Column
    @CreatedDate
    private String modifiedBy;
    @JsonIgnore
    @Column
    @LastModifiedDate
    private Date modifiedDate;

}
