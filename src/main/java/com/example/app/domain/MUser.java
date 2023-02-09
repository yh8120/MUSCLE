package com.example.app.domain;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
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
	private String role;
	private Sex sex;
	private String iconPath;
	private Date registered;
	

}
