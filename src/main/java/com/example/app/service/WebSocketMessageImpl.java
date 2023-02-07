package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
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
		MessagePayload payload = dao.findLogAsMessagePayloadById(trainingLogId);

		template.convertAndSend("/topic/greetings", payload);
	}

	@Override
	@Async
	public void sendToUser(Integer userId) throws Exception {
		Thread.sleep(1500);
		List <MessagePayload> payloadList = dao.findLogListAsMessagePayloadNewer();

		for(MessagePayload payload:payloadList) {
			
			template.convertAndSendToUser(String.valueOf(userId),"/private", payload);
		}
	}
}
