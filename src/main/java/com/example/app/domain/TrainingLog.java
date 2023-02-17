package com.example.app.domain;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class TrainingLog {

	private Integer id;
	private MUser user;
	private Training training;
	private Double totalWeight;
	private Double oneRepMax;
	private Double maxWeight;
	private Double latitude;
	private Double logitude;
	@Valid
	private List<TrainingSet> trainingSetList;
	private List<Integer> contributorList;
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private Date registered;
}
