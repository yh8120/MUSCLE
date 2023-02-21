package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.ReservedEmailDao;
import com.example.app.dao.UserDao;
import com.example.app.domain.MUser;
import com.example.app.domain.ReservedEmail;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;
	@Autowired
	ReservedEmailDao reservedEmailDao;

	@Override
	public MUser getUser(String email) throws Exception {
		return userDao.selectByEmail(email);
	}

	@Override
	public ReservedEmail getReservedEmail(String uuid) throws Exception {
		ReservedEmail reservedEmail = reservedEmailDao.selectByUuid(uuid);
		return reservedEmail;
	}

	@Override
	public void updateEmail(ReservedEmail reservedEmail) throws Exception {
		MUser user = userDao.selectByEmail(reservedEmail.getEmail());
		user.setEmail(reservedEmail.getNewEmail());
		userDao.update(user);
		reservedEmailDao.delete(reservedEmail.getUuid());
		
	}

}
