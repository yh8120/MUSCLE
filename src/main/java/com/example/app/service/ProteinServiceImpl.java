package com.example.app.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dao.ProteinDao;
import com.example.app.domain.RestProtein;

@Service
@Transactional
public class ProteinServiceImpl implements ProteinService {

	@Autowired
	ProteinDao dao;
	
	@Override
	public void addProtein(RestProtein restProtein) throws Exception {
		dao.insert(restProtein);
	}

	@Override
	public void delProtein(RestProtein restProtein) throws Exception {
		dao.delete(restProtein);
	}

}
