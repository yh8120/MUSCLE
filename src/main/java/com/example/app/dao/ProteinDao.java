package com.example.app.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.app.domain.Protein;
@Mapper
public interface ProteinDao {

	void insert(Protein protein) throws Exception;

	void delete(Protein protein) throws Exception;
}
