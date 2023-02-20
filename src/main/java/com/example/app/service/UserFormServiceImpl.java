package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.TempUserDao;
import com.example.app.dao.UserDao;
import com.example.app.dao.UserRegisterDao;
import com.example.app.domain.MUser;
import com.example.app.domain.TempUser;
import com.example.app.domain.UserForm;

@Service
@Transactional
public class UserFormServiceImpl implements UserFormService {

	@Autowired
	UserDao userDao;
	@Autowired
	TempUserDao tempUserDao;
	@Autowired
	UserRegisterDao userRegisterDao;
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public void createAccount(UserForm userForm) throws Exception {
		MUser user = new MUser();
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setLoginPass(encoder.encode(userForm.getLoginPass()));
		user.setBirthday(userForm.getBirthday());
		user.setSex(userForm.getSex());
		user.setIconPath(userForm.getIconPath());
		userDao.insert(user);
		userRegisterDao.deleteByEmail(userForm.getEmail());
	}

	@Override
	public void setTempUser(UserForm userForm,String uuid) throws Exception {
		tempUserDao.insert(new TempUser(userForm,uuid));
	}
	
	@Override
	public void updateAccount(UserForm userForm) throws Exception {
		MUser user = new MUser();
		user.setId(userForm.getId());
		user.setName(userForm.getName());
		user.setEmail(userForm.getEmail());
		user.setLoginPass(encoder.encode(userForm.getLoginPass()));
		user.setBirthday(userForm.getBirthday());
		user.setSex(userForm.getSex());
		user.setIconPath(userForm.getIconPath());
		userDao.update(user);
		
	}



	
	

	
}
