package com.example.app.service;

import java.util.List;

import com.example.app.domain.MessagePayload;
import com.example.app.domain.Training;
import com.example.app.domain.TrainingLog;
import com.example.app.domain.TrainingPart;

public interface TrainingService {

	List<MessagePayload> getTrainingLogByMessagePayloads() throws Exception;

	List<TrainingPart> getTrainingListOrderByPart(Integer userId) throws Exception;

	Training getTraining(Integer trainingId) throws Exception;

	void addTraining(Training training) throws Exception;

	void editTraining(Training training) throws Exception;

	void deleteTraining(Integer trainingId) throws Exception;

	Training getAllTrainingLog(Integer trainingId) throws Exception;

	TrainingLog getTrainingLog(Integer trainingLogId) throws Exception;

	Integer addTrainingLog(TrainingLog trainingLog) throws Exception;

	void editTrainingLog(TrainingLog trainingLog) throws Exception;

	void deleteTrainingLog(TrainingLog trainingLog) throws Exception;
}
