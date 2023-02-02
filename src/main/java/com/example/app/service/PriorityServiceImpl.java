package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.PriorityDao;
import com.example.app.domain.Priority;
@Service
public class PriorityServiceImpl implements PriorityService {

	@Autowired
	PriorityDao dao;
	
	@Override
	public List<Priority> getPriorityList() throws Exception {
		return dao.selectAll();
	}

}
