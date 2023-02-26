package com.example.app.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ListChartData {

	private Date registered;
	private List<TrainingPart> trainingPartList;
	
}
