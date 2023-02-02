package com.example.app.domain;

import javax.validation.constraints.NotBlank;

import org.mindrot.jbcrypt.BCrypt;

import lombok.Data;

@Data
public class Login {

	@NotBlank
	private String email;

	@NotBlank
	private String loginPass;

	public boolean isCorrectPassword(String hashedPassword) {
		return BCrypt.checkpw(loginPass, hashedPassword);
	}

}
