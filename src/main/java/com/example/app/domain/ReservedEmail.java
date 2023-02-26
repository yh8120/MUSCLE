package com.example.app.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservedEmail {

	private Integer id;
	private String email;
	private String newEmail;
	private Date registered;
	private String uuid;

}
