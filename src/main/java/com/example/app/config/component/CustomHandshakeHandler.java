package com.example.app.config.component;

import java.security.Principal;
import java.util.Map;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import com.example.app.domain.StompPrincipal;
@Component
public class CustomHandshakeHandler extends DefaultHandshakeHandler {
	@Override
	protected Principal determineUser(
			ServerHttpRequest request,
			WebSocketHandler wsHandler,
			Map<String, Object> attributes) {
		String userName = request.getHeaders().get("sec-websocket-key").get(0);
		System.out.println("username="+userName);
		return new StompPrincipal(userName);
	}
}
