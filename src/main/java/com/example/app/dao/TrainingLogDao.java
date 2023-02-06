package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.MessagePayload;
import com.example.app.domain.Training;
import com.example.app.domain.TrainingLog;

@Mapper
public interface TrainingLogDao {

	Training findAllbyTrainingId(Integer trainingId)throws Exception;
	
	List<MessagePayload> findLogListAsMessagePayloadNewer()throws Exception;
	
	MessagePayload findLogAsMessagePayloadById(Integer trainingLogId)throws Exception;
	
	TrainingLog findById(Integer id) throws Exception;

	void insert(TrainingLog trainingLog) throws Exception;

	void update(TrainingLog trainingLog) throws Exception;

	void delete(Integer trainingLogId) throws Exception;

}
