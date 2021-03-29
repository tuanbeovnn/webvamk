package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.api.input.ChangeInfoInput;
import com.vamkthesis.web.api.input.UpdateRoleInput;
import com.vamkthesis.web.api.input.UserUpdateInput;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.dto.UserDto;
import com.vamkthesis.web.entity.RoleEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.exception.ClientException;
import com.vamkthesis.web.exception.EmailExistsException;
import com.vamkthesis.web.repository.IRoleRepository;
import com.vamkthesis.web.repository.UserRepository;
import com.vamkthesis.web.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IRoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private IUserService userService;
    @Autowired
    private EmailService emailService;
    @Value("${hostName}")
    private String account;

    @Value("${jwt.secret}")
    private String secret;
    @Override
    public UserDto save(UserDto dto) throws MessagingException {

        UserEntity userEntity = Converter.toModel(dto, UserEntity.class);
            UserEntity userEntity1 = userRepository.findOneByEmail(dto.getEmail());
            if (userEntity1 != null) {
                throw new EmailExistsException();
            }
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (String roles : dto.getRoles()){
            RoleEntity roleEntity = roleRepository.findOneByName(roles);
            if (roleEntity == null){
                throw new ClientException("Your role could not find");
            }
            roleEntities.add(roleEntity);
        }
        userEntity.setRoles(roleEntities);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntity = userRepository.save(userEntity);
        UserDto userDTO = Converter.toModel(userEntity, UserDto.class);
        return userDTO;
    }
    @Override
    public UserUpdateInput updateInfo(UserUpdateInput userUpdateInput) {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity  = userRepository.findById(userUpdateInput.getId()).get();
        userEntity.setAddress(userUpdateInput.getAddress());
        userEntity.setAvatar(userUpdateInput.getAvatar());
        userEntity.setBio(userUpdateInput.getBio());
        userEntity.setBirthDay(userUpdateInput.getBirthDay());
        userEntity.setGender(userUpdateInput.getGender());
        userEntity.setName(userUpdateInput.getName());
        userEntity.setPhone(userUpdateInput.getPhone());
        userEntity.setUsername(userUpdateInput.getUsername());
        List<RoleEntity> roleEntities = new ArrayList<>();
        for (String roles : userUpdateInput.getRoles()){
            RoleEntity roleEntity = roleRepository.findOneByName(roles);
            if (roleEntity == null){
                throw new ClientException("Your role could not find");
            }
            roleEntities.add(roleEntity);
        }
        userEntity.setRoles(roleEntities);
        userEntity = userRepository.save(userEntity);
        UserUpdateInput userUpdateInput1 = Converter.toModel(userEntity, UserUpdateInput.class);
        return userUpdateInput1;
    }

    @Override
    public void sendEmailVerify(String email) throws MessagingException {
        UserEntity userEntity = userRepository.findOneByEmail(email);
        if (userEntity == null) {
            throw new ClientException("Your email does not exits");
        }
        long expire = System.currentTimeMillis() + 1000 * 60 * 20;
        String resend = userEntity.getEmail();

        if (userEntity.getVerifyAccount() == 1) {
            throw new ClientException("Email has been verified already !");
        }
        String signature = DigestUtils.sha256Hex(resend + expire + secret);
        String message = String.format("<h2>Click a link below to VERIFY your account</h2><a style='background-color:green;color:white;font-size:50px;text-decoration: none;padding:0px 50px;'href=' %s/api/auth/verify?email=%s&expire=%d&signature=%s'>Verify Email</a>",account, email,expire, signature);
        emailService.sendMail(resend, "VERIFY ACCOUNT", message);
    }

    @Override
    public boolean changePassword(ChangeInfoInput changeInfoInput) {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findById(myUserDTO.getId()).get();
        if (passwordEncoder.matches(changeInfoInput.getOldPassword(), userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(changeInfoInput.getNewPassword()));
            userRepository.save(userEntity);
            return true;
        }else throw new ClientException("Your old password not correct");

    }

    @Override
    public void updateRole(Long id, UpdateRoleInput updateRoleInput) {
        UserEntity userEntity = userRepository.findById(id).get();
        List<RoleEntity> roleEntities = roleRepository.findAllById(updateRoleInput.getIdRoles());
        userEntity.setRoles(roleEntities);
        userEntity = userRepository.save(userEntity);
    }

    @Override
    public void delete(long[] ids) {
        for (long item : ids){
           UserEntity userEntity = userRepository.findById(item).get();
           userRepository.delete(userEntity);

        }
//        userRepository.findAllById()
    }

    @Override
    public UserDto findUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).get();
        UserDto dto = Converter.toModel(userEntity,UserDto.class);
        return dto;
    }

    @Override
    public void verifyAccount(String email) {
        UserEntity userEntity = userRepository.findOneByEmail(email);
        userEntity.setVerifyAccount(1);
        userEntity = userRepository.save(userEntity);
    }


}
