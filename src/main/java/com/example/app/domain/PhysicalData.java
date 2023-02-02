package com.example.app.domain;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class PhysicalData {

	private Integer id;
	private MUser user;
	@NotBlank
	@Range(min=1,max=500)
	private Double bodyWeight;
	@Range(min=1,max=100)
	private Double bodyFat;
	private Date registered;
}
