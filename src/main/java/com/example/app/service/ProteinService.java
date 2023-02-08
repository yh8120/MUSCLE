package com.example.app.service;

import com.example.app.domain.Protein;

public interface ProteinService {

	void addProtein(Protein restProtein) throws Exception;

	void delProtein(Protein restProtein) throws Exception;

}
