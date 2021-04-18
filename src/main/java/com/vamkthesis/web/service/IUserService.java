package com.vamkthesis.web.service;


import com.vamkthesis.web.api.input.ChangeInfoInput;
import com.vamkthesis.web.api.input.UpdateRoleInput;
import com.vamkthesis.web.api.input.UserUpdateInput;
import com.vamkthesis.web.api.output.UserOutput;
import com.vamkthesis.web.dto.UserDto;
import com.vamkthesis.web.paging.PageList;
import org.springframework.data.domain.Pageable;

import javax.mail.MessagingException;

public interface IUserService {
    UserDto save(UserDto userDto) throws MessagingException;
    void delete (long[] ids);
    void deleteById(long id);
    UserDto findUserById(Long id);
    void verifyAccount(String email);
    UserUpdateInput updateInfo(UserUpdateInput userUpdateInput);
    void sendEmailVerify(String email) throws MessagingException;
    boolean changePassword(ChangeInfoInput changeInfoInput);
    UserOutput updateRole(Long id, UpdateRoleInput updateRoleInput);
    PageList<UserOutput> findAllByRoles(Pageable pageable);
}
