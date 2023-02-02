package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Priority;

@Service
public interface PriorityService {

	List<Priority> getPriorityList()throws Exception;
}
