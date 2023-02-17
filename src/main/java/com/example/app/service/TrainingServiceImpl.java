package com.example.app.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.TrainingDao;
import com.example.app.dao.TrainingLogDao;
import com.example.app.dao.TrainingSetDao;
import com.example.app.domain.Training;
import com.example.app.domain.TrainingLog;
import com.example.app.domain.TrainingPart;
import com.example.app.domain.TrainingSet;

@Service
@Transactional
public class TrainingServiceImpl implements TrainingService {

	@Autowired
	TrainingDao dao;
	@Autowired
	TrainingLogDao logDao; 
	@Autowired
	TrainingSetDao setDao;
	
	@Override
	public List<TrainingPart> getTrainingListOrderByPart(Integer userId) throws Exception {
		return dao.findAllOrderByPart(userId);
	}

	@Override
	public Training getAllTrainingLog(Integer trainingId) throws Exception {
		return logDao.findAllbyTrainingId(trainingId);
	}

	@Override
	public TrainingLog addTrainingLog(TrainingLog trainingLog) throws Exception {
		Double totalWeight = 0.0;
		Double oneRepMax = 0.0;
		Double maxWeight = 0.0;
		for (TrainingSet trainingSet : trainingLog.getTrainingSetList()) {
			if (oneRepMax < trainingSet.getOneRepMax())oneRepMax = trainingSet.getOneRepMax();
			if (maxWeight < trainingSet.getWeight())maxWeight = trainingSet.getWeight();
			totalWeight+= trainingSet.getWeight()*trainingSet.getRep();
		}
		trainingLog.setOneRepMax(oneRepMax);
		trainingLog.setMaxWeight(maxWeight);
		trainingLog.setTotalWeight(totalWeight);
		logDao.insert(trainingLog);
		
		for(TrainingSet trainingSet:trainingLog.getTrainingSetList()) {
			trainingSet.setTrainingLogId(trainingLog.getId());
			setDao.insert(trainingSet);
		}
		
		dao.updateLastTrainingDay((trainingLog.getTraining()).getId());
		
		return trainingLog;

	}

	@Override
	public TrainingLog getTrainingLog(Integer trainingLogId) throws Exception {
		return logDao.findById(trainingLogId);
	}

	@Override
	public void editTrainingLog(TrainingLog trainingLog) throws Exception {
		Double totalWeight = 0.0;
		Double oneRepMax = 0.0;
		Double maxWeight = 0.0;
		for (TrainingSet trainingSet : trainingLog.getTrainingSetList()) {
			if (oneRepMax < trainingSet.getOneRepMax())oneRepMax = trainingSet.getOneRepMax();
			if (maxWeight < trainingSet.getWeight())maxWeight = trainingSet.getWeight();
			totalWeight+= trainingSet.getWeight()*trainingSet.getRep();
		}
		trainingLog.setOneRepMax(oneRepMax);
		trainingLog.setMaxWeight(maxWeight);
		trainingLog.setTotalWeight(totalWeight);
		logDao.update(trainingLog);
		
		setDao.deleteAll(trainingLog.getId());
		
		for(TrainingSet trainingSet:trainingLog.getTrainingSetList()) {
			trainingSet.setTrainingLogId(trainingLog.getId());
			setDao.insert(trainingSet);
		}
		
		dao.updateLastTrainingDay((trainingLog.getTraining()).getId());
	}

	@Override
	public void deleteTrainingLog(TrainingLog trainingLog) throws Exception {
		setDao.deleteAll(trainingLog.getId());
		logDao.delete(trainingLog.getId());
		dao.updateLastTrainingDay((trainingLog.getTraining()).getId());
	}

	@Override
	public void addTraining(Training training) throws Exception {
		dao.insert(training);
		
	}

	@Override
	public Training getTraining(Integer trainingId) throws Exception {
		return dao.findbyTrainingId(trainingId);
	}

	@Override
	public void editTraining(Training training) throws Exception {
		dao.update(training);
		
	}

	@Override
	public void deleteTraining(Integer trainingId) throws Exception {
		dao.delete(trainingId);
		
	}


}
