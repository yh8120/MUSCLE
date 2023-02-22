package com.example.app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class WebSocketSecurityConfig
		extends AbstractSecurityWebSocketMessageBrokerConfigurer {

	protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
		messages
				.simpTypeMatchers(
						SimpMessageType.CONNECT,
						SimpMessageType.HEARTBEAT,
						SimpMessageType.UNSUBSCRIBE,
						SimpMessageType.DISCONNECT,
						SimpMessageType.OTHER)
				.authenticated()//指定したmessageタイプは認証が必要
				.simpSubscribeDestMatchers("/user/**", "/topic/notice/**").authenticated()//指定したサブスクは認証が必要
				.anyMessage().denyAll();//上記以外のメッセージはすべて拒否
	}
}