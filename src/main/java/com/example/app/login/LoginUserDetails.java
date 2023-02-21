package com.example.app.login;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.app.domain.MUser;

public class LoginUserDetails implements UserDetails {

	private final MUser loginUser;
	private final Collection<? extends GrantedAuthority> authorities;
	
	public LoginUserDetails(MUser loginUser) {
		this.loginUser = loginUser;
		this.authorities = loginUser.getRoleList().stream().map(role -> new SimpleGrantedAuthority(role)).toList();
	}
	
	public MUser getLoginUser() {return loginUser;}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {return authorities;}

	@Override
	public String getPassword() {return loginUser.getLoginPass();}

	@Override
	public String getUsername() {return loginUser.getEmail();}

	@Override
	public boolean isAccountNonExpired() {return true;}

	@Override
	public boolean isAccountNonLocked() {return true;}

	@Override
	public boolean isCredentialsNonExpired() {return true;}

	@Override
	public boolean isEnabled() {return true;}

	
	

}
