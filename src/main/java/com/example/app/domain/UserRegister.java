package com.example.app.domain;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class UserRegister {

	private Integer id;
	@NotBlank
	@Email
	private String email;
	private String uuid;
	private Date registered;
	
}
