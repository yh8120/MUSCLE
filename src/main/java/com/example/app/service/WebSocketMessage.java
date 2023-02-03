package com.example.app.service;

public interface WebSocketMessage {

	public void send(Integer trainingLogId)throws Exception;
	
	public void sendToUser(Integer userId)throws Exception;
}
