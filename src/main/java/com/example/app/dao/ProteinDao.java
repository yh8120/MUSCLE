package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.RestProtein;
@Mapper
public interface ProteinDao {

	void insert(RestProtein restProtein) throws Exception;

	void delete(RestProtein restProtein) throws Exception;
}
