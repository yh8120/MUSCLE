package com.example.app.service;

import org.springframework.web.socket.messaging.SessionConnectedEvent;

import com.example.app.domain.TrainingLog;

public interface WebSocketMessageService {

	public void sendTrainingLog(TrainingLog trainingLog)throws Exception;

	public void handleWebSocketConnectListener(SessionConnectedEvent event)throws Exception;
	
	public void sendProteinToUser(Integer trainingLogId)throws Exception;
	
	public void sendConnection(String userName)throws Exception;
}
