package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Weekday;

@Mapper
public interface WeekdayDao {

	List<Weekday> selectAll() throws Exception;
}
