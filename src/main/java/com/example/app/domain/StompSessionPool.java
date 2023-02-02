package com.example.app.domain;

import java.util.List;

public class StompSessionPool {

	private List<String> principalNameList;

	public void put (String principalName) {
		 this.principalNameList.add(principalName);
	 }
	public List<String> getAll(){
		return this.principalNameList;
	}
 
}
