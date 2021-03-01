package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class UsernamePasswordAuthenticationTokenCustom extends UsernamePasswordAuthenticationToken {
    public UsernamePasswordAuthenticationTokenCustom(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public UsernamePasswordAuthenticationTokenCustom(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);

    }

}
