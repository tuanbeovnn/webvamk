package com.vamkthesis.web.service;


import com.vamkthesis.web.api.input.UserUpdateInput;
import com.vamkthesis.web.dto.UserDto;

import javax.mail.MessagingException;

public interface IUserService {
    UserDto save(UserDto userDto) throws MessagingException;
    void delete (long[] ids);
    UserDto findUserById(Long id);
    void verifyAccount(String email);
    UserUpdateInput updateInfo(UserUpdateInput userUpdateInput);
    void sendEmailVerify(String email) throws MessagingException;
}
