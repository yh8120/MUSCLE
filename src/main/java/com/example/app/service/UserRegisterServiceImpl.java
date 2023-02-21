package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.UserDao;
import com.example.app.dao.UserRegisterDao;
import com.example.app.domain.MUser;
import com.example.app.domain.UserRegister;

@Service
@Transactional
public class UserRegisterServiceImpl implements UserRegisterService {

	@Autowired
	UserDao userDao;
	@Autowired
	UserRegisterDao dao;

	@Override
	public UserRegister findByRegistrationCode(String registrationCode) throws Exception {
		return dao.findByRegistrationCode(registrationCode);
	}

	@Override
	public void setUserRegister(UserRegister userRegister) throws Exception {
		dao.deleteByEmail(userRegister.getEmail());
		dao.insert(userRegister);
	}

	@Override
	public void deleteByEmail(String email) throws Exception {
		dao.deleteByEmail(email);
	}

	@Override
	public void deleteOldRecode() throws Exception {
		dao.deleteOldRecode();
	}

	@Override
	public MUser checkUserbyEmail(String email) throws Exception {
		MUser user = userDao.selectByEmail(email);
		return user;
	}

}
