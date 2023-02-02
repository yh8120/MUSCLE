package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.UserDao;
import com.example.app.dao.UserRegisterDao;
import com.example.app.domain.MUser;
import com.example.app.domain.UserForm;

@Service
@Transactional
public class UserFormServiceImpl implements UserFormService {

	@Autowired
	UserDao userDao;
	@Autowired
	UserRegisterDao userRegisterDao;
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public void createAccount(UserForm userform) throws Exception {
		MUser user = new MUser();
		user.setName(userform.getName());
		user.setEmail(userform.getEmail());
		user.setLoginPass(encoder.encode(userform.getLoginPass()));
		user.setBirthday(userform.getBirthday());
		user.setSex(userform.getSex());
		userDao.insert(user);
		userRegisterDao.deleteByEmail(userform.getEmail());
	}

	
}
