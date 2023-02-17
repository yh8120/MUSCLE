package com.example.app.domain;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Notice {

	private Integer trainingLogId;
	private Integer userId;
	private String body;
	private String iconPath;
	private List<Integer> contributorList;

}
