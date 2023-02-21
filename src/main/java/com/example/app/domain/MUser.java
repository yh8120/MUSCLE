package com.example.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MUser {
	
	private Integer id;
	@NotBlank
	@Size(max=20)
	private String name;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String loginPass;
	@Past
	@NotNull
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date birthday;
	private List<String> roleList;
	private Sex sex;
	private String iconPath;
	private Date registered;
	
	public MUser(UserForm userForm) {
		this.id=userForm.getId();
		this.name=userForm.getName();
		this.email=userForm.getEmail();
		this.loginPass=userForm.getLoginPass();
		this.birthday=userForm.getBirthday();
		this.sex=userForm.getSex();
		this.iconPath=userForm.getIconPath();
	}
}
