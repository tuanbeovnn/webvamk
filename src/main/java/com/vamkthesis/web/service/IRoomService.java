package com.vamkthesis.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vamkthesis.web.api.input.RoomInput;
import com.vamkthesis.web.dto.RoomDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRoomService {
    RoomDto save(RoomInput roomInput) throws JsonProcessingException;

    List<RoomDto> findAllByMessageNotRead(Pageable pageable);

}
