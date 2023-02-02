package com.example.app.service;

public interface MessageSenderService {
	public void sendMessage(String toAddress,String subject,String body) throws Exception;

}
