package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.entity.RoleEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JwtUserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

//        UsernamePasswordAuthenticationTokenCustom authenticationTokenToken = (UsernamePasswordAuthenticationTokenCustom) SecurityContextHolder.getContext().getAuthentication();
        UserEntity userEntity = userRepository.findOneByEmail(email);
        if(userEntity == null){
            throw new UsernameNotFoundException("User not found");
        }
        MyUserDTO user = Converter.toModel(userEntity, MyUserDTO.class);
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().clear();
        for(RoleEntity roleEntity : userEntity.getRoles()){
            user.getRoles().add(roleEntity.getName());
            authorities.add(new SimpleGrantedAuthority(roleEntity.getName()));
        }
        user.setAuthorities(authorities);
        return user;
    }
}
