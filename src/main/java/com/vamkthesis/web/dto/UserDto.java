package com.vamkthesis.web.dto;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class UserDto extends AbstractDto{

    public static final int PASSWORD_MIN_LENGTH = 4;
    public static final int PASSWORD_MAX_LENGTH = 100;
    @Size(min = 5, max = 100)
    @NotEmpty(message = "Name is required")
    protected String name;
    @NotEmpty(message = "Email is mandatory")
    protected String email;

    protected String address;
    @NotEmpty(message = "Phone number is required")
    protected String phone;
    protected String username;

    @NotNull
    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    @NotEmpty(message = "Password is required")
    protected String password;

    protected String avatar;
    protected String bio;
    protected String birthDay;
    protected int gender;
    protected List<String> roles = new ArrayList<>();
    protected int verifyAccount;

}
