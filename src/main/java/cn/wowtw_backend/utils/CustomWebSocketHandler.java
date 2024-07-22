package cn.wowtw_backend.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class CustomWebSocketHandler extends TextWebSocketHandler {

    private static final ConcurrentHashMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = Objects.requireNonNull(session.getUri()).getQuery().split("=")[1];
        sessions.put(username, session);
    }

    @Override
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws Exception {
        // 处理接收到的消息（如果需要）
    }

    @Override
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        sessions.values().remove(session);
    }

    public void notifySessionInvalid(String username) {
        WebSocketSession session = sessions.get(username);
        if (session != null && session.isOpen()) {
            try {
                session.sendMessage(new TextMessage("Session invalid"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}