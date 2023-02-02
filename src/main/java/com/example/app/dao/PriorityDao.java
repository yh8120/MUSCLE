package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Priority;

@Mapper
public interface PriorityDao {

	List<Priority> selectAll() throws Exception;
}
