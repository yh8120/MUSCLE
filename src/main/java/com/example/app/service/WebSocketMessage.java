package com.example.app.service;

public interface WebSocketMessage {

	public void send(Integer trainingLogId)throws Exception;
	
	public void sendToUser(String UserName)throws Exception;
}
