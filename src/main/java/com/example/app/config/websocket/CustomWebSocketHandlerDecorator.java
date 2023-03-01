package com.example.app.config.websocket;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

public class CustomWebSocketHandlerDecorator extends WebSocketHandlerDecorator {

	private List<String> sessions = new CopyOnWriteArrayList<>();

	public CustomWebSocketHandlerDecorator(WebSocketHandler delegate) {
		super(delegate);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session.getPrincipal().getName());
		System.out.println(sessions);
		super.getDelegate().afterConnectionEstablished(session);
	}

	@Override
	public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
		super.getDelegate().handleMessage(session, message);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		sessions = sessions.stream()
				.filter(userName -> !userName.equals(session.getPrincipal().getName()))
				.collect(Collectors.toCollection(CopyOnWriteArrayList::new));
		System.out.println(sessions);
		super.getDelegate().afterConnectionClosed(session, closeStatus);
	}
}
