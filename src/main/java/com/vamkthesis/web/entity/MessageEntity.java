package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class MessageEntity extends BaseEntity {
    private String content;
    private String roomNumber;
    private Long idUser;
}
