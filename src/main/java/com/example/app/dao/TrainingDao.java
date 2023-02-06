package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Training;
import com.example.app.domain.TrainingPart;

@Mapper
public interface TrainingDao {

	List<TrainingPart> findAllOrderByPart(Integer userId) throws Exception;
	
	Training findbyTrainingId(Integer trainingId)throws Exception;
	
	void insert(Training training)throws Exception;
	
	void updateLastTrainingDay(Integer trainingId)throws Exception;

}
