package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.UserRegister;

@Mapper
public interface UserRegisterDao {

	UserRegister findByRegistrationCode(String RegistrationCode) throws Exception;

	void insert(UserRegister userRegister) throws Exception;

	void deleteByEmail(String email) throws Exception;

	void deleteOldRecode() throws Exception;
}
