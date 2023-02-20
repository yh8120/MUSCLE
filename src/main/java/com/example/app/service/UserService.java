package com.example.app.service;

import com.example.app.domain.MUser;

public interface UserService {
	MUser getUser(String email)throws Exception;

}
