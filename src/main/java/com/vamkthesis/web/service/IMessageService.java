package com.vamkthesis.web.service;

import com.vamkthesis.web.dto.MessageDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMessageService {
    void save(MessageDto messageDto);
    List<MessageDto> findAllByNewest(String room, Pageable pageable);
}
