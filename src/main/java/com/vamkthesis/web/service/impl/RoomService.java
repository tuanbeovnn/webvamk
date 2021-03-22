package com.vamkthesis.web.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vamkthesis.web.api.input.RoomInput;
import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.RoomDto;
import com.vamkthesis.web.entity.RoomEntity;
import com.vamkthesis.web.repository.IRoomRepository;
import com.vamkthesis.web.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IRoomRepository roomRepository;

    @Override
    public RoomDto save(RoomInput roomInput) throws JsonProcessingException {
        RoomEntity roomEntity = Converter.toModel(roomInput, RoomEntity.class);
        ObjectMapper objectMapper = new ObjectMapper();
        String clientInfo = objectMapper.writeValueAsString(roomInput);
        roomEntity.setClientInfo(clientInfo);
        roomEntity.setRoomId(UUID.randomUUID().toString());
        roomEntity.setStaffId(0L);
        roomEntity = roomRepository.save(roomEntity);
        RoomDto roomDto = Converter.toModel(roomEntity, RoomDto.class);
        return roomDto;
    }

    @Override
    public List<RoomDto> findAllByMessageNotRead(Long id, Pageable pageable) {

        List<RoomEntity> roomEntities = roomRepository.findAllByMessageNotRead(id, pageable);
        List<RoomDto> roomDtos = Converter.toList(roomEntities, RoomDto.class);
        return roomDtos;
    }


}
