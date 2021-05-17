package com.vamkthesis.web.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailSubcribeDto {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String message;
}
