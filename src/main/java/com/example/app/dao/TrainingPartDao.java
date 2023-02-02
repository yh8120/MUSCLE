package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.TrainingPart;

@Mapper
public interface TrainingPartDao {

	List<TrainingPart> selectAll() throws Exception;
}
