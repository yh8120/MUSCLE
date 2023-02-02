package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.TrainingType;

@Mapper
public interface TrainingTypeDao {

	List<TrainingType> selectAll() throws Exception;
}
