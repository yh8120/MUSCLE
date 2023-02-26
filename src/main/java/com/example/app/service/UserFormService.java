package com.example.app.service;

import com.example.app.domain.UserForm;

public interface UserFormService {

	void createAccount(UserForm userForm)throws Exception;
	
	void reservationEmail(UserForm userForm,String oldEmail,String uuid)throws Exception;
	
	void updateAccount(UserForm userForm)throws Exception;
}
