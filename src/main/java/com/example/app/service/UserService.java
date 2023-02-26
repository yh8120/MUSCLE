package com.example.app.service;

import com.example.app.domain.MUser;
import com.example.app.domain.ReservedEmail;

public interface UserService {
	MUser getUser(String email)throws Exception;
	
	ReservedEmail getReservedEmail(String uuid)throws Exception;
	
	void updateEmail(ReservedEmail reservedEmail)throws Exception;

}
