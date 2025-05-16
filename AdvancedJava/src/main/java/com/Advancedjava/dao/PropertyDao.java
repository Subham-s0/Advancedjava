package com.Advancedjava.dao;

import java.util.List;
import com.Advancedjava.model.Propertymodel;
import com.Advancedjava.model.Propertymodel.PropertyStatus;
import com.Advancedjava.exception.DataAccessException;

public interface PropertyDao {
    Propertymodel findById(int propertyId) throws DataAccessException;
    List<Propertymodel> findallproperties() throws DataAccessException;
    int save(Propertymodel property) throws DataAccessException;
    boolean update(Propertymodel property) throws DataAccessException;
    boolean delete(int propertyId) throws DataAccessException;
    List<Propertymodel> findByStatus(PropertyStatus status) throws DataAccessException;
    List<Propertymodel> findByCity(String city) throws DataAccessException;
    boolean Propertyexists(String name, String address, String city, String country) 
            throws DataAccessException;
        List<Propertymodel> listAllPropertiesByCategory(int categoryId) throws DataAccessException;

}