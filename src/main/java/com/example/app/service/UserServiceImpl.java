package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.UserDao;
import com.example.app.domain.MUser;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao dao;

	@Override
	public MUser getUser(String email) throws Exception {
		return dao.selectByEmail(email);
	}

}
