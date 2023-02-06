package com.example.app.service;

import java.util.List;

import com.example.app.domain.Weekday;

public interface WeekdayService {
	
	List<Weekday> getWeekdayList()throws Exception;

}
