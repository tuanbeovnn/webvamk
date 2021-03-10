package com.vamkthesis.web.service.impl;

import com.vamkthesis.web.convert.Converter;
import com.vamkthesis.web.dto.MessageDto;
import com.vamkthesis.web.entity.MessageEntity;
import com.vamkthesis.web.repository.IMessageRepository;
import com.vamkthesis.web.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService implements IMessageService {
    @Autowired
    private IMessageRepository messageRepository;
    @Override
    public void save(MessageDto messageDto) {
        MessageEntity messageEntity = Converter.toModel(messageDto, MessageEntity.class);
        messageEntity = messageRepository.save(messageEntity);
    }
}
