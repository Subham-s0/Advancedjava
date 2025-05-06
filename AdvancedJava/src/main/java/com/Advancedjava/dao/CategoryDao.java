package com.Advancedjava.dao;

import com.Advancedjava.model.Categorymodel;

import java.util.List;

import com.Advancedjava.exception.DataAccessException;

public interface CategoryDao {
    boolean existsByCategoryName(String categoryName) throws DataAccessException;
    Categorymodel findById(int categoryId) throws DataAccessException;
    List<Categorymodel> findAllcategories() throws DataAccessException;
}