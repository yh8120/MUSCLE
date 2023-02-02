package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.TrainingPartDao;
import com.example.app.domain.TrainingPart;
@Service
public class TrainingPartServiceImpl implements TrainingPartService {

	@Autowired
	TrainingPartDao dao;
	
	@Override
	public List<TrainingPart> getTrainingPartList() throws Exception {
		return dao.selectAll();
	}

}
