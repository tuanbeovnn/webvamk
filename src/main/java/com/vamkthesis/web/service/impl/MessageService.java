package com.vamkthesis.web.service.impl;

import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MessageDto;
import com.vamkthesis.web.dto.MyUserDTO;
import com.vamkthesis.web.entity.MessageEntity;
import com.vamkthesis.web.entity.RoomEntity;
import com.vamkthesis.web.repository.IMessageRepository;
import com.vamkthesis.web.repository.IRoomRepository;
import com.vamkthesis.web.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private IMessageRepository messageRepository;
    @Autowired
    private IRoomRepository roomRepository;

    /**
     * @param messageDto
     * @return
     * @TuanNguyen
     */
    @Override
    public MessageDto save(MessageDto messageDto) {
        MessageEntity messageEntity = Converter.toModel(messageDto, MessageEntity.class);
        messageEntity = messageRepository.save(messageEntity);
        return Converter.toModel(messageEntity, MessageDto.class);
    }

    /**
     * @param room
     * @param pageable
     * @return
     * @TuanNguyen
     */
    @Override
    public List<MessageDto> findAllByNewest(String room, Pageable pageable) {
        MyUserDTO myUserDTO = (MyUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<MessageEntity> messageEntity = messageRepository.findAllByNewest(room, pageable);
        List<MessageDto> messageDto = Converter.toList(messageEntity, MessageDto.class);
        if (myUserDTO.getRoles().contains("STAFF")) {
            RoomEntity roomEntity = roomRepository.findAllByRoomId(room);
            if (roomEntity.getStaffId() == 0) {
                roomEntity.setStaffId(myUserDTO.getId());
                roomRepository.save(roomEntity);
            }
        }
        return messageDto;
    }

}
