package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.TrainingSet;

@Mapper
public interface TrainingSetDao {

	void insert(TrainingSet trainingSet) throws Exception;

	void update(TrainingSet trainingSet) throws Exception;

	void deleteAll(Integer trainingLogId) throws Exception;

}
