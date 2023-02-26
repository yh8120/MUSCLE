package com.example.app.domain;

import java.util.List;

import lombok.Data;

@Data
public class TrainingPart {

	private Integer id;
	private String name;
	private List<Training> trainingList;
	private Double totalWeight;
}
