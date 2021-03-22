package com.vamkthesis.web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
public class RoomEntity extends BaseEntity {
    private Long staffId;
    private String clientInfo;
    private String roomId;
}
