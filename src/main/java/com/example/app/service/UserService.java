package com.example.app.service;

import com.example.app.domain.MUser;

public interface UserService {
	MUser getUserbyLogin(String email)throws Exception;

}
