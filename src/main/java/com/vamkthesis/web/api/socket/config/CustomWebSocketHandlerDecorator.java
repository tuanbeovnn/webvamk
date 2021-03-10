package com.vamkthesis.web.api.socket.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

public class CustomWebSocketHandlerDecorator
        extends WebSocketHandlerDecorator {
    protected final Log logger = LogFactory.getLog(CustomWebSocketHandlerDecorator.class);

    public CustomWebSocketHandlerDecorator(WebSocketHandler delegate) {
        super(delegate);
    }

    @Override
    public void handleMessage(final WebSocketSession session, final WebSocketMessage<?> message)
            throws Exception {
        logger.info("incomming message" + message.getPayload().toString());
        if (message instanceof TextMessage) {
            TextMessage msg = (TextMessage) message;
            String payload = msg.getPayload();
//            only add \00 if not present (iOS / Android)
            if (!payload.substring(payload.length() - 1).equals("\u0000") && !payload.equals("\n")) {
                final TextMessage message1 = new TextMessage(payload + "\u0000");
                super.handleMessage(session, message1);
                return;
            }
        }
        super.handleMessage(session, message);
    }
}