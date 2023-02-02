package com.example.app.domain;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
@Data
public class UserForm {
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
	@DateTimeFormat(pattern="yyy-MM-dd")
	private Date birthday;
	private Sex sex;
	@AssertTrue
	private Boolean agreement;
}
