package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.MUser;
import com.example.app.domain.TempUser;

@Mapper
public interface TempUserDao {

	MUser selectByUuid(String uuid)throws Exception;
	
	void insert(TempUser tempUser)throws Exception;
	
	void delete(Integer id)throws Exception;
}
