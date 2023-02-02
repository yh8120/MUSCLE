package com.example.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Sex;

@Mapper
public interface SexDao {

	List<Sex> selectAll() throws Exception;
}
