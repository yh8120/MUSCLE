package com.example.app.domain;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class TrainingSet {

	private Integer id;
	private Integer trainingLogId;
	@Range(min=0,max=1000)
	private Double weight;
	@NotNull
	@Range(min=1)
	private Integer rep;
	@NotNull
	private Integer order;
	@NotNull
	private Double oneRepMax;
}
