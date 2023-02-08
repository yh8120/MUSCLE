package com.example.app.service;

import com.example.app.domain.MUser;
import com.example.app.domain.UserRegister;

public interface UserRegisterService {

	MUser checkUserbyEmail(String email)throws Exception;
	
	UserRegister findByRegistrationCode(String RegistrationCode) throws Exception;
	
	void setUserRegister(UserRegister userRegister) throws Exception;

	void deleteByEmail(String email) throws Exception;

	void deleteOldRecode() throws Exception;
}
