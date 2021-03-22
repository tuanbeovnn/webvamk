package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class MessageEntity extends BaseEntity  {
    private String content;
    private String roomNumber;
    private Long idUser;
}
