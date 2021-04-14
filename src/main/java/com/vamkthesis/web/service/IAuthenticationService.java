package com.vamkthesis.web.service;


import com.vamkthesis.web.api.input.LoginInput;
import com.vamkthesis.web.dto.TokenDto;

public interface IAuthenticationService {
    boolean saveToken(TokenDto dto);
    TokenDto refreshToken(String token, String refreshToken);
    boolean logout();
    TokenDto login(LoginInput loginInfo);

}
