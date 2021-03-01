package com.vamkthesis.web.service.impl;


import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.UserDto;
import com.vamkthesis.web.entity.RoleEntity;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.exception.ClientException;
import com.vamkthesis.web.exception.EmailExistsException;
import com.vamkthesis.web.repository.IRoleRepository;
import com.vamkthesis.web.repository.UserRepository;
import com.vamkthesis.web.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.apache.commons.codec.digest.DigestUtils;

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
        UserEntity userEntity = new UserEntity();
        if (dto.getId() != null) {
            UserEntity oldUser = userRepository.findById(dto.getId()).get();
            userEntity = Converter.toModel(dto, oldUser.getClass());
        } else {
            userEntity = Converter.toModel(dto, UserEntity.class);
            UserEntity userEntity1 = userRepository.findOneByEmail(dto.getEmail());
            if (userEntity1 != null) {
                throw new EmailExistsException();
            }
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
        long expire = System.currentTimeMillis()+1000 * 60 * 20;
        String email = userDTO.getEmail();
        String signature = DigestUtils.sha256Hex(email + expire + secret);
        String message = String.format("<h2>Click a link below to VERIFY your account</h2><a style='background-color:green;color:white;font-size:50px;text-decoration: none;padding:0px 50px;'href=' %s/api/auth/verify?email=%s&expire=%d&signature=%s'>Verify Email</a>",account, email,expire, signature);
        emailService.sendMail(email, "VERIFY ACCOUNT", message);
        return userDTO;
    }

    @PostAuthorize("#id == authentication.principal.id")
    @RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
    public ResponseEntity updateProfile(@RequestBody UserDto userDTO, @PathVariable("id") long id) throws MessagingException {
        userDTO.setId(id);
        UserDto userDTO1 = userService.save(userDTO);
        return ResponseEntityBuilder.getBuilder().setMessage("Update user successfully").setDetails(userDTO1).build();

    }

    @Override
    public void delete(long[] ids) {
        for (long item : ids){
            userRepository.deleteById(item);
        }
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
