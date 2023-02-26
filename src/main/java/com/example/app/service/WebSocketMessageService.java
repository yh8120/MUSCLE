package com.example.app.service;

import com.example.app.domain.TrainingLog;

public interface WebSocketMessageService {

	public void sendTrainingLog(TrainingLog trainingLog)throws Exception;

	public void sendTrainingLogToUser(String UserName)throws Exception;
	
	public void sendProteinToUser(Integer trainingLogId)throws Exception;
}
