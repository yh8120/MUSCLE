package com.example.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.app.dao.UserDao;
import com.example.app.login.LoginUserDetails;

@Service
public class UpdateSecurityContextImpl implements UpdateSecurityContext {
	
	@Autowired
	UserDao dao;

	@Override
	public void update() throws Exception {
		SecurityContext context = SecurityContextHolder.getContext();
		String email = context.getAuthentication().getName();
		LoginUserDetails userDetails = new LoginUserDetails(dao.selectByEmail(email));
		
		context.setAuthentication(new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities()));

	}

}
