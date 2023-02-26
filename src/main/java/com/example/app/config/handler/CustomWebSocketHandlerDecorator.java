package com.example.app.config.handler;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.WebSocketHandlerDecorator;

import com.example.app.service.WebSocketMessageService;
import com.example.app.service.WebSocketMessageServiceImpl;

public class CustomWebSocketHandlerDecorator extends WebSocketHandlerDecorator {
	public CustomWebSocketHandlerDecorator(WebSocketHandler delegate) {
		super(delegate);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("接続完了:" + session.getPrincipal().getName());
		WebSocketMessageService service = new WebSocketMessageServiceImpl();
		service.sendConnection(session.getPrincipal().getName());
		super.afterConnectionEstablished(session);
	}

}
