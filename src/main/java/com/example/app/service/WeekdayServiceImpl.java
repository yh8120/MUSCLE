package com.example.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.WeekdayDao;
import com.example.app.domain.Weekday;
@Service
public class WeekdayServiceImpl implements WeekdayService {

	@Autowired
	WeekdayDao dao;
	
	@Override
	public List<Weekday> getWeekdayList() throws Exception {
		return dao.selectAll();
	}

}
