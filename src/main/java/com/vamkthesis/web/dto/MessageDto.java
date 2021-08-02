package com.vamkthesis.web.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MessageDto extends AbstractDto<MessageDto> {
    //    private String id;
    private String content;
    private String roomNumber;
    private Long idUser;
}
