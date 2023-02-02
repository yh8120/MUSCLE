package com.example.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.example.app.config.component.CustomHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Autowired
	private CustomHandshakeHandler customHandshakeHandler;

//	@Autowired
//	private UserInterceptor userInterceptor;

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/topic");
		config.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// クライアントから接続する際のエンドポイントを"/endpoint"に設定する。
		// ハンドシェイク(接続が確立する際の)ハンドラとしてcustomHandshakeHandlerを設定する。
		// customHandshakeHandlerは独自で実装しているクラスで、
		// コネクションに対してユーザーの定義を行っている。
		registry.addEndpoint("/endpoint")
				.setHandshakeHandler(customHandshakeHandler)
				.withSockJS();
	}
}
