package com.example.app.domain;

import java.util.Date;

import lombok.Data;

@Data
public class Protein {

	private String id;
	private String name;
	private TrainingLog trainingLog;
	private Message message;
	private Date registered;
}
