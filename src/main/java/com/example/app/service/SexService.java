package com.example.app.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.app.domain.Sex;

@Service
public interface SexService {

	List<Sex> getSexList()throws Exception;
}
