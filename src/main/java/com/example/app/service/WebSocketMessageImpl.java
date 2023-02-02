package com.example.app.service;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.example.app.dao.TrainingLogDao;
import com.example.app.domain.MessagePayload;

@Service
public class WebSocketMessageImpl implements WebSocketMessage {

	@Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private TrainingLogDao dao;

	@Override
	public void send(Integer trainingLogId) throws Exception {
		MessagePayload payload = dao.findLogAsMessagePayloadById(95);

		template.convertAndSend("/topic/greetings", payload);
	}

	@Override
	public void sendToUser(Principal principal, Integer trainingLogId) throws Exception {
		MessagePayload payload = new MessagePayload();

		String toUser = principal.getName();
		template.convertAndSendToUser(toUser,"/topic/greetings", payload);
	}
}
