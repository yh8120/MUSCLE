package com.example.app.service;

import com.example.app.domain.RestProtein;

public interface ProteinService {

	void addProtein(RestProtein restProtein) throws Exception;

	void delProtein(RestProtein restProtein) throws Exception;

}
