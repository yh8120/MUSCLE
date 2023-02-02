package com.example.app.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessagePayload {

	private Integer trainingLogId;
	private Integer userId;
	private String iconPath;
	private String trainingName;
	private List<TrainingSet> trainingSetList;
	private List<Integer> contributorList;
}
