package com.vamkthesis.web.api;

import com.fasterxml.uuid.Generators;
import com.vamkthesis.web.api.input.ChangeInfoInput;
import com.vamkthesis.web.api.input.LoginInput;
import com.vamkthesis.web.api.input.TokenInput;
import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.config.JwtTokenUtil;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.*;
import com.vamkthesis.web.entity.UserEntity;
import com.vamkthesis.web.exception.ClientException;
import com.vamkthesis.web.exception.EmailOrPasswordNotCorrectException;
import com.vamkthesis.web.repository.UserRepository;
import com.vamkthesis.web.service.IAuthenticationService;
import com.vamkthesis.web.service.IUserService;
import com.vamkthesis.web.service.impl.AuthenticationService;
import com.vamkthesis.web.service.impl.EmailService;
import com.vamkthesis.web.service.impl.JwtUserService;
import com.vamkthesis.web.service.impl.UploadService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.google.api.Google;
import org.springframework.social.google.api.impl.GoogleTemplate;
import org.springframework.social.google.api.userinfo.GoogleUserInfo;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.util.Random;
import java.util.UUID;

@RequestMapping("/api/auth")
@RestController
public class AuthenticationApi {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenUtil tokenProvider;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    IAuthenticationService tokenService;
    @Autowired
    JwtUserService jwtUserService;

    @Autowired
    private IUserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UploadService uploadService;

    @Value("${jwt.secret}")
    private String secret;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity registration(@RequestBody @Valid UserDto userDto) throws MessagingException {
        UserDto dto = userService.save(userDto);
        userService.sendEmailVerify(userDto.getEmail());
        return ResponseEntityBuilder.getBuilder().setMessage("Create user successfully").setDetails(dto).build();

    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Login Success", response = AuthToken.class),
    })
    public ResponseEntity login(@RequestBody LoginInput loginInfo) {
            Authentication authentication = null;
            Authentication auth = new UsernamePasswordAuthenticationTokenCustom(loginInfo.getEmail(), loginInfo.getPassword());
            SecurityContextHolder.getContext().setAuthentication(auth);
            try {
                authentication = authenticationManager.authenticate(auth);
            }catch (Exception e) {
                throw new EmailOrPasswordNotCorrectException();
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDTO myUserDTO = (MyUserDTO) authentication.getPrincipal();
//            myUserDTO.setRoles();
            final String token = tokenProvider.generateToken(myUserDTO);
            if (myUserDTO.getVerifyAccount()==0) {
                throw new ClientException("Your Account has not been verified");
            }
            UUID refreshToken = UUID.randomUUID();
            TokenDto tokenDTO = new TokenDto();
            tokenDTO.setAccessToken(token);
            tokenDTO.setRefreshToken(refreshToken.toString());
//            tokenDTO.setEnvoke(false);

            tokenService.saveToken(tokenDTO);
//            res.put("token", token);
            return ResponseEntityBuilder.getBuilder().setMessage("Login success").setDetails(tokenDTO).build();

    }

    @RequestMapping(value = "/google/login", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "true : success, false: failed"),
    })
    public ResponseEntity loginGoogle(@RequestBody TokenInput tokenInput) {
        Google google = new GoogleTemplate(tokenInput.getAccessToken());
        GoogleUserInfo userInfo = google.userOperations().getUserInfo();
        UserEntity userEntity = userRepository.findOneByEmail(userInfo.getEmail());
        if (userEntity == null) {
            userEntity = new UserEntity();
            userEntity.setEmail(userInfo.getEmail());
            userEntity.setName(userInfo.getName());
            String username = userInfo.getEmail().substring(0, userInfo.getEmail().indexOf("@")) + "_"
                    + UUID.randomUUID().toString().substring(0, 3);
            userEntity.setUsername(username);
            String avatar = google.userOperations().getUserInfo().getProfilePictureUrl();
            userEntity.setAvatar(avatar);
            userRepository.save(userEntity);
        }
        Authentication auth = new UsernamePasswordAuthenticationTokenCustom("", "");
        SecurityContextHolder.getContext().setAuthentication(auth);
        MyUserDTO myUserDTO = (MyUserDTO) jwtUserService.loadUserByUsername(userInfo.getEmail());
        auth = new UsernamePasswordAuthenticationTokenCustom(myUserDTO, "");
        SecurityContextHolder.getContext().setAuthentication(auth);
        final String token = tokenProvider.generateToken(myUserDTO);
        UUID refreshToken = Generators.randomBasedGenerator(new Random()).generate();
        TokenDto tokenDTO = new TokenDto();
        tokenDTO.setRefreshToken(refreshToken.toString());
        tokenDTO.setAccessToken(token);
        tokenService.saveToken(tokenDTO);
        return ResponseEntity.status(200).body(tokenDTO);
    }

    @RequestMapping(value = "/facebook/login", method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "true : success, false: failed"),
    })
    public ResponseEntity loginFacebook(@RequestBody TokenInput tokenInput) {

        Facebook facebook = new FacebookTemplate(tokenInput.getAccessToken());
        String[] fields = {"id", "email", "name", "gender"};
        User userProfile = facebook.fetchObject("me", User.class, fields);
        UserEntity userEntity = userRepository.findOneByEmail(userProfile.getEmail());
        if (userEntity == null) {
            UserEntity userEntity1 = Converter.toModel(userProfile, UserEntity.class);
            String username = userProfile.getEmail().substring(0, userProfile.getEmail().indexOf("@")) + "_"
                    + UUID.randomUUID().toString().substring(0, 3);
            userEntity1.setUsername(username);
            byte[] imageBytes = facebook.userOperations().getUserProfileImage();
//            String avatar = uploadService.saveImage(imageBytes, UUID.randomUUID().toString() + ".jpg");
//            String avatar = amazonClient.uploadFileFb(UUID.randomUUID().toString() + ".jpg", imageBytes);
//            userEntity1.setAvatar(avatar);
            userRepository.save(userEntity1);
        }
        Authentication auth = new UsernamePasswordAuthenticationToken("", "");
        SecurityContextHolder.getContext().setAuthentication(auth);
        MyUserDTO myUserDTO = (MyUserDTO) jwtUserService.loadUserByUsername(userProfile.getEmail());
        auth = new UsernamePasswordAuthenticationToken(myUserDTO, "");
        SecurityContextHolder.getContext().setAuthentication(auth);
        final String token = tokenProvider.generateToken(myUserDTO);
        UUID refreshToken = Generators.randomBasedGenerator(new Random()).generate();
        TokenDto tokenDTO = new TokenDto();
        tokenDTO.setRefreshToken(refreshToken.toString());
        tokenDTO.setAccessToken(token);
        tokenService.saveToken(tokenDTO);
        return ResponseEntity.status(200).body(tokenDTO);
    }



    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "true : success, false: failed"),
    })
    public ResponseEntity logout() {
        authenticationService.logout();
        return ResponseEntityBuilder.getBuilder().setMessage("Logout successfully").build();
    }

    @RequestMapping(value = "/verify", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "true : success, false: failed"),
    })
    public ResponseEntity verifyAccount(@Valid @ModelAttribute EmailDto emailDto) throws MessagingException {
        if (!DigestUtils.sha256Hex(emailDto.getEmail()+emailDto.getExpire()+ secret).equals(emailDto.getSignature())){
            throw new ClientException("Signature not correct");
        }
        if (System.currentTimeMillis() > emailDto.getExpire()){
            throw new ClientException("Expired");
        }
        userService.verifyAccount(emailDto.getEmail());
        emailService.sendMail(emailDto.getEmail(),"Verify account Successfully","");
        return ResponseEntityBuilder.getBuilder().setMessage("Your account has been verified successfully").build();
    }

    @RequestMapping(value = "/forgot", method = RequestMethod.GET)
    public ResponseEntity forgotPassword(@RequestParam String email) throws MessagingException {
        long expire = System.currentTimeMillis() + 1000 * 60 * 20;
        String signature = DigestUtils.sha256Hex(email + expire + secret);
        String message = String.format("<h2>Click a link below to reset your password</h2><a style='background-color:green;color:white;font-size:50px;text-decoration: none;padding:0px 50px;'href='http://saunakovaasa.ml:8080/api/auth/resetPassword?email=%s&expire=%d&signature=%s'>RESET Email</a>", email, expire, signature);
        emailService.sendMail(email, "FORGOT PASSWORD", message);
        return ResponseEntityBuilder.getBuilder().setMessage("Email has been sent already !").build();
    }

    @ResponseBody
    @RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
    public ResponseEntity resetPassword(@Valid @ModelAttribute EmailDto emailDTO) throws MessagingException {
        if (!DigestUtils.sha256Hex(emailDTO.getEmail() + emailDTO.getExpire() + secret).equals(emailDTO.getSignature()))
            throw new ClientException("Signature not correct");
        if (System.currentTimeMillis() > emailDTO.getExpire()) throw new ClientException("Expired");
        UUID randomPassWord = Generators.randomBasedGenerator(new Random()).generate();
        UserEntity userEntity = userRepository.findOneByEmail(emailDTO.getEmail());
        String newPassword = randomPassWord.toString().substring(0, 6);
        userEntity.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userEntity);
        emailService.sendMail(emailDTO.getEmail(), "RESET PASSWORD", "Current Password: " + newPassword);
        return ResponseEntityBuilder.getBuilder().setMessage("Your password has been reset and sent to your email").build();
    }

    @RequestMapping(value = "/changePassword", method = RequestMethod.POST)
    public ResponseEntity changePassword(@RequestBody ChangeInfoInput changeInfoInput) {
        if (userService.changePassword(changeInfoInput)) {
            return ResponseEntityBuilder.getBuilder().setMessage("Your password had been changed successfully").build();
        } else {
            return ResponseEntityBuilder.getBuilder().setMessage("Could not change your passoword").build();
        }
    }
}
