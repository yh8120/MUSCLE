package com.example.app.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.app.domain.MUser;
import com.example.app.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService service;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		MUser loginUser = null;

		try {
			loginUser = service.getUser(username);
			System.out.println(loginUser);
			if (loginUser == null) {
				throw new UsernameNotFoundException("user not found");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new LoginUserDetails(loginUser);
	}
}
