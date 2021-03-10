package com.vamkthesis.web.api.socket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.support.GenericMessage;

import java.util.HashMap;
import java.util.Map;

public class MessageBuilder {
    private Map res;

    private MessageBuilder() {
    }

    public static MessageBuilder getBuilder() {
        MessageBuilder MessageBuilder = new MessageBuilder();
        MessageBuilder.res = new HashMap();
        return MessageBuilder;
    }

    public MessageBuilder set(String key, Object object) {
        res.put(key, object);
        return this;
    }

    public GenericMessage<byte[]> build() throws JsonProcessingException {
        return new GenericMessage<>(new ObjectMapper().writeValueAsBytes(res));
    }
}
