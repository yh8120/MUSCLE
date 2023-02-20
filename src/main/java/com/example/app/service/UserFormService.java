package com.example.app.service;

import com.example.app.domain.UserForm;

public interface UserFormService {

	void createAccount(UserForm userForm)throws Exception;
	
	void setTempUser(UserForm userform,String uuid)throws Exception;
	
	void updateAccount(UserForm userForm)throws Exception;
}
