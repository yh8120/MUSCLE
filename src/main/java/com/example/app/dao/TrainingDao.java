package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Training;
import com.example.app.domain.TrainingPart;
import com.example.app.domain.Weekday;

@Mapper
public interface TrainingDao {

	List<TrainingPart> findAllOrderByPart(Integer userId) throws Exception;

	List<Weekday> findAllOrderByWeekday(Integer userId) throws Exception;
	
	Training findbyTrainingId(Integer trainingId)throws Exception;
	
	void updateLastTrainingDay(Integer trainingId)throws Exception;

}
