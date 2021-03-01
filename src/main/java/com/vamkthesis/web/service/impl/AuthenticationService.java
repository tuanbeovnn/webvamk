package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.config.JwtTokenUtil;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.dto.TokenDto;
import com.vamkthesis.web.entity.TokenEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.exception.ClientException;
import com.vamkthesis.web.repository.ITokenRepository;
import com.vamkthesis.web.repository.UserRepository;
import com.vamkthesis.web.service.IAuthenticationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService implements IAuthenticationService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ITokenRepository tokenRepository;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Override
    public boolean saveToken(TokenDto dto) {
        TokenEntity tokenEntity = Converter.toModel(dto, TokenEntity.class);
        MyUserDTO myUserDto = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findById(myUserDto.getId()).get();
        tokenEntity.setUser(userEntity);
        tokenRepository.save(tokenEntity);
        return true;
    }

    @Override
    public TokenDto refreshToken(String token, String refreshToken) {
        TokenEntity entity = tokenRepository.findByRefreshToken(refreshToken);
        if (entity == null) throw new ClientException("Something wrong with token");
        UserEntity userEntity = entity.getUser();
        UUID randomRefreshTk = UUID.randomUUID();
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        String subject = jwtTokenUtil.getUsernameFromToken(token);
        String accessToken = jwtTokenUtil.doGenerateToken(claims,subject);
        TokenDto dto = new TokenDto();
        dto.setRefreshToken(randomRefreshTk.toString());
        dto.setAccessToken(accessToken);
        TokenEntity tokenEntity = Converter.toModel(dto,TokenEntity.class);
        tokenEntity.setUser(userEntity);
        tokenRepository.save(tokenEntity);
        return dto;
    }

    @Override
    public boolean logout() {
        MyUserDTO myUserDto = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TokenEntity tokenEntity = tokenRepository.findOneUserId(myUserDto.getId());
        tokenEntity.setEnvoke(true);
        tokenRepository.save(tokenEntity);
        return true;
    }
}
