package com.vamkthesis.web.api.input;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserUpdateInput extends AbtractInput {

    protected String name;
    protected String address;
    protected String phone;
    protected String username;
    protected String avatar;
    protected String bio;
    protected String birthDay;
    protected int gender;
    protected List<String> roles = new ArrayList<>();
}
