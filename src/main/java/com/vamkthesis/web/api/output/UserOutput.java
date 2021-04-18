package com.vamkthesis.web.api.output;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserOutput {
    protected long id;
    protected String name;
    protected String email;
    protected String address;
    protected String phone;
    protected String username;
    protected String avatar;
    protected List<String> roles = new ArrayList<>();

}
