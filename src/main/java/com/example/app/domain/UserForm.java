package com.example.app.domain;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
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
public class UserForm {
	private Integer id;
	@NotBlank
	@Size(max=20)
	private String name;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	private String loginPass;
	private String loginPassCopy;
	@Past
	@NotNull
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date birthday;
	private Sex sex;
	private String iconPath;
	@AssertTrue
	private Boolean agreement;
	
	public UserForm(MUser user) {
		this.id=user.getId();
		this.name=user.getName();
		this.email=user.getEmail();
		this.birthday=user.getBirthday();
		this.iconPath=user.getIconPath();
		Sex sex = user.getSex();
		this.sex=sex;
	}
}
