package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.TrainingPart;

@Service
public interface TrainingPartService {

	List<TrainingPart> getTrainingPartList()throws Exception;
}
