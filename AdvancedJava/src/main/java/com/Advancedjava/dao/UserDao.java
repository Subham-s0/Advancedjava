package com.Advancedjava.dao;

import java.util.List;

import com.Advancedjava.exception.DataAccessException;
import com.Advancedjava.model.usermodel;

public interface UserDao {

	
	  usermodel findByUsernameOrEmail(String identifier) throws DataAccessException;
	  usermodel findByUserId(String identifier) throws DataAccessException;
	    boolean existsByEmail(String email) throws DataAccessException;
	    boolean existsByUsername(String username) throws DataAccessException;
	    boolean existsByPhone(String phone) throws DataAccessException;
	    int saveUser(usermodel user) throws DataAccessException;
	    boolean updateUser(usermodel user) throws DataAccessException;
	    List<usermodel> getAllUsers() throws DataAccessException;
	}
