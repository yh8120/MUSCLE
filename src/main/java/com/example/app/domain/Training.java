package com.example.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class Training {

	private Integer id;
	private Integer userId;
	@NotBlank
	@Size(max=45)
	private String name;
	private TrainingPart trainingPart;
	private TrainingType trainingType;
	private Priority priority;
	private Weekday weekday;
	private Date lastTrainingDay;
	private Date registered;
	
	List<TrainingLog> trainingLogList;
}
