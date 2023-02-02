package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.TrainingTypeDao;
import com.example.app.domain.TrainingType;
@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

	@Autowired
	TrainingTypeDao dao;
	
	@Override
	public List<TrainingType> getTrainingTypeList() throws Exception {
		return dao.selectAll();
	}
}
