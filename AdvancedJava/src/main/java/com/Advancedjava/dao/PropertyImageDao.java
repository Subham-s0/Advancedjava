package com.Advancedjava.dao;

import java.util.List;
import com.Advancedjava.model.PropertyImagemodel;
import com.Advancedjava.exception.DataAccessException;

public interface PropertyImageDao {
    List<PropertyImagemodel> findByPropertyId(int propertyId) throws DataAccessException;
    int save(PropertyImagemodel image, int propertyId) throws DataAccessException;
    boolean delete(int imageId) throws DataAccessException;
    
    boolean deleteByPropertyId(int propertyId) throws DataAccessException;
}