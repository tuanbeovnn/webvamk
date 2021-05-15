package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "rooms")
public class RoomEntity extends BaseEntity {
    private Long staffId;
    private String clientInfo;
    private String roomId;
}
