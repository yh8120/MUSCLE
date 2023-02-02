package com.example.app.service;

import java.security.Principal;

public interface WebSocketMessage {

	public void send(Integer trainingLogId)throws Exception;
	
	public void sendToUser(Principal principal,Integer trainingLogId)throws Exception;
}
