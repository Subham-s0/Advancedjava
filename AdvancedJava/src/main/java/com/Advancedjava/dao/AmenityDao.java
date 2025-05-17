package com.Advancedjava.dao;

import java.util.List;

import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.AmenityModel;

public interface AmenityDao {

	List<AmenityModel> findAll() throws DataAccessException;

	AmenityModel findById(int amenityId) throws DataAccessException;

	int add(AmenityModel amenity) throws DataAccessException;

	boolean delete(int amenityId) throws DataAccessException;

	boolean update(AmenityModel amenity) throws DataAccessException;

}
