package com.vamkthesis.web.api.socket.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import javax.persistence.Id;
import java.util.Date;

@RedisHash("token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Token {

    @Id
    private String id;
    @Indexed
    private String accessToken;
    private String refreshToken;
    private boolean revoke;
    private Date createdDate;
    private Date expire;
}
