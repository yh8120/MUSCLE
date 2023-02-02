package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.MUser;

@Mapper
public interface UserDao {

	MUser selectByEmail(String email)throws Exception;
	
	void insert(MUser user)throws Exception;
}
