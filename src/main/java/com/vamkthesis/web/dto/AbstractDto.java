package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
public class AbstractDto<T> {

    private Long id;
    //    private String createdBy;
    private Date createdDate;
//    private String modifiedBy;
//    private Date modifiedDate;

}
