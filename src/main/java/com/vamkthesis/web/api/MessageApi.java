package com.vamkthesis.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.api.socket.handler.MessageBuilder;
import com.vamkthesis.web.dto.MessageDto;
import com.vamkthesis.web.service.impl.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/message")
public class MessageApi {
    @Autowired
    private MessageService messageService;
    @Autowired
    SimpMessagingTemplate template;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody MessageDto messageDto) throws JsonProcessingException {
        MessageDto messageDto1 =  messageService.save(messageDto);
        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder().set("data",messageDto1).build();
        template.send(String.format("/topic/%s", messageDto1.getRoomNumber()), genericMessage);
        return ResponseEntityBuilder.getBuilder().setMessage("Save room successfully").setDetails(messageDto1).build();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<MessageDto> getListNewest(@RequestParam String room, Pageable pageable) {
        List<MessageDto> messageDtos = messageService.findAllByNewest(room, pageable);
        return messageDtos;
    }
}
