package com.example.app.login;

import java.util.List;

import lombok.Data;

@Data
public class LoginUser{
	private Integer id;
	private String email;
	private String name;
	private String password;
	private List<String> roleList;

}
