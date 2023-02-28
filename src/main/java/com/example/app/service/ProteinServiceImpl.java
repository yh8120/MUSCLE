package com.example.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.dao.ProteinDao;
import com.example.app.domain.Protein;

@Service
@Transactional
public class ProteinServiceImpl implements ProteinService {

	@Autowired
	ProteinDao dao;
	
	@Override
	public void addProtein(Protein protein) throws Exception {
		dao.insert(protein);
	}

	@Override
	public void delProtein(Protein protein) throws Exception {
		dao.delete(protein);
	}

}
