package com.example.app.service;

import com.example.app.domain.UserForm;

public interface UserFormService {

	void createAccount(UserForm userform)throws Exception;
	
	void updateAccount(UserForm userform)throws Exception;
}
