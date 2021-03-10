package com.vamkthesis.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vamkthesis.web.api.input.RoomInput;
import com.vamkthesis.web.api.output.ResponseEntityBuilder;
import com.vamkthesis.web.api.socket.handler.MessageBuilder;
import com.vamkthesis.web.dto.RoomDto;
import com.vamkthesis.web.service.impl.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/room")
public class RoomApi {
    @Autowired
    private RoomService roomService;
    @Autowired
    SimpMessagingTemplate template;


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity save(@RequestBody RoomInput roomInput) throws JsonProcessingException {
        RoomDto roomDto =  roomService.save(roomInput);
        GenericMessage<byte[]> genericMessage = MessageBuilder.getBuilder().set("data",roomDto).build();
        template.send(String.format("/topic/%s", "0000"), genericMessage);
        return ResponseEntityBuilder.getBuilder().setMessage("Save room successfully").setDetails(roomDto).build();
    }


}
