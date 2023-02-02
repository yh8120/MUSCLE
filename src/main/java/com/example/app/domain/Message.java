package com.example.app.domain;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Message {

	private Integer id;
	private MUser user;
	@NotBlank
	@Size(max=20)
	private String note;
	private Double latitude;
	private Double logitude;
	private Long numberProtein;
	private Date registered;

}
