package com.vamkthesis.web.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vamkthesis.web.api.input.RoomInput;
import com.vamkthesis.web.dto.RoomDto;

public interface IRoomService {
    RoomDto save(RoomInput roomInput) throws JsonProcessingException;
}
