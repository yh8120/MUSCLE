package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.SexDao;
import com.example.app.domain.Sex;

@Service
public class SexServiceImpl implements SexService {

	@Autowired
	SexDao dao;
	
	@Override
	public List<Sex> getSexList() throws Exception {
		return dao.selectAll();
	}

}
