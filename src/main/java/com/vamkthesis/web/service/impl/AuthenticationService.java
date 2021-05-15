package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.api.input.LoginInput;
import com.vamkthesis.web.config.JwtTokenUtil;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.dto.TokenDto;
import com.vamkthesis.web.entity.TokenEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.exception.ClientException;
import com.vamkthesis.web.exception.EmailOrPasswordNotCorrectException;
import com.vamkthesis.web.repository.ITokenRepository;
import com.vamkthesis.web.repository.UserRepository;
import com.vamkthesis.web.service.IAuthenticationService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil tokenProvider;

    @Autowired
    IAuthenticationService tokenService;

    @Override
    public boolean saveToken(TokenDto dto) {
        TokenEntity tokenEntity = Converter.toModel(dto, TokenEntity.class);
        MyUserDTO myUserDto = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findById(myUserDto.getId()).get();
        tokenEntity.setUser(userEntity);
        String secretToken = dto.getAccessToken().substring(dto.getAccessToken().lastIndexOf(".") + 1);
        tokenEntity.setSecretToken(secretToken);
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
        String accessToken = jwtTokenUtil.doGenerateToken(claims, subject);
        TokenDto dto = new TokenDto();
        dto.setRefreshToken(randomRefreshTk.toString());
        dto.setAccessToken(accessToken);
        TokenEntity tokenEntity = Converter.toModel(dto, TokenEntity.class);
        tokenEntity.setUser(userEntity);
        tokenRepository.save(tokenEntity);
        return dto;
    }

    @Override
    public boolean logout() {
        MyUserDTO myUserDto = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TokenEntity tokenEntity = tokenRepository.findOneUserId(myUserDto.getId());
        tokenEntity.setEnvoke(1);
        tokenRepository.save(tokenEntity);
        return true;
    }

    /**
     * @TuanNguyen
     * @param loginInfo
     * @return
     */
    @Override
    public TokenDto login(LoginInput loginInfo) {
        Authentication authentication = null;
        Authentication auth = new UsernamePasswordAuthenticationToken(loginInfo.getEmail(), loginInfo.getPassword());
        SecurityContextHolder.getContext().setAuthentication(auth);
        try {
            authentication = authenticationManager.authenticate(auth);
        } catch (Exception e) {
            throw new EmailOrPasswordNotCorrectException();
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        MyUserDTO myUserDTO = (MyUserDTO) authentication.getPrincipal();
//            myUserDTO.setRoles();
        final String token = tokenProvider.generateToken(myUserDTO);
        if (myUserDTO.getVerifyAccount() == 0) {
            throw new ClientException("Your Account has not been verified");
        }
        UUID refreshToken = UUID.randomUUID();
        TokenDto tokenDTO = new TokenDto();
        tokenDTO.setAccessToken(token);
        tokenDTO.setRefreshToken(refreshToken.toString());
//            tokenDTO.setEnvoke(false);

        tokenService.saveToken(tokenDTO);
        return tokenDTO;
    }
}
