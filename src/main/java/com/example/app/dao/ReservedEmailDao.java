package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.ReservedEmail;

@Mapper
public interface ReservedEmailDao {

	ReservedEmail selectByUuid(String uuid)throws Exception;
	
	void delete(String uuid)throws Exception;
	
	void insert(ReservedEmail reservedEmail)throws Exception;
	
	void deleteOldRecode()throws Exception;
}
