package com.vamkthesis.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.api.socket.handler.MessageBuilder;
import com.vamkthesis.web.dto.MessageDto;
import com.vamkthesis.web.service.impl.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/message")
public class MessageApi {
    @Autowired
    private MessageService messageService;
    @Autowired
    SimpMessagingTemplate template;
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody MessageDto messageDto) throws JsonProcessingException {
        messageService.save(messageDto);
        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder().set("data",messageDto).build();
        template.send(String.format("/topic/%s", messageDto.getRoomNumber()), genericMessage);
        return ResponseEntityBuilder.getBuilder().setMessage("Save message successfully").build();
    }
}
