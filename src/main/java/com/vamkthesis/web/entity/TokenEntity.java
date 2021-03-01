package com.vamkthesis.web.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "tokens")
public class TokenEntity extends BaseEntity{
    @Lob
    @Column( length = 100000 )
    private String accessToken;

    private String refreshToken;
    private int expires;
    private boolean envoke;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
