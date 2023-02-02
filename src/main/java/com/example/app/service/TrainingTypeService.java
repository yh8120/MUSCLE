package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.TrainingType;

@Service
public interface TrainingTypeService {

	List<TrainingType> getTrainingTypeList()throws Exception;
}
