package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.ReservedEmailDao;
import com.example.app.dao.UserDao;
import com.example.app.dao.UserRegisterDao;
import com.example.app.domain.MUser;
import com.example.app.domain.ReservedEmail;
import com.example.app.domain.UserForm;

@Service
@Transactional
public class UserFormServiceImpl implements UserFormService {

	@Autowired
	UserDao userDao;
	@Autowired
	ReservedEmailDao reservedEmailDao;
	@Autowired
	UserRegisterDao userRegisterDao;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	UpdateSecurityContext updateSecurityContext;

	@Override
	public void createAccount(UserForm userForm) throws Exception {
		MUser user = new MUser(userForm);
		user.setLoginPass(encoder.encode(user.getLoginPass()));
		userDao.insert(user);
		userRegisterDao.deleteByEmail(userForm.getEmail());
	}

	@Override
	public void updateAccount(UserForm userForm) throws Exception {
		MUser user = new MUser(userForm);
		user.setLoginPass(encoder.encode(user.getLoginPass()));
		userDao.update(user);
		updateSecurityContext.update();
	}

	@Override
	public void reservationEmail(UserForm userForm, String oldEmail, String uuid) throws Exception {
		ReservedEmail reservedEmail = new ReservedEmail();
		reservedEmail.setEmail(oldEmail);
		reservedEmail.setNewEmail(userForm.getEmail());
		reservedEmail.setUuid(uuid);
		reservedEmailDao.insert(reservedEmail);
	}

}
