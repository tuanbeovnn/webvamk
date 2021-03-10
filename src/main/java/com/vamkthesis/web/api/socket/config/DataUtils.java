package com.vamkthesis.web.api.socket.config;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Getter
public class DataUtils {
    public Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
}
